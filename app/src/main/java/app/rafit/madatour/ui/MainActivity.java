package app.rafit.madatour.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import app.rafit.madatour.R;
import app.rafit.madatour.api.ApiClient;
import app.rafit.madatour.api.ApiService;
import app.rafit.madatour.model.TouristSite;
import app.rafit.madatour.model.TouristSiteAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.res.Resources;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TouristSiteAdapter adapter;
    private List<TouristSite> touristSiteList;
    private EditText editTextSearch;
    private String BASE_URL;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BASE_URL = getApplicationContext().getString(R.string.api_base_url);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        touristSiteList = new ArrayList<>();
        adapter = new TouristSiteAdapter(touristSiteList, this);
        recyclerView.setAdapter(adapter);

        System.out.println("ETO ");
        fetchTouristSites();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        // Set up the swipe-to-refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh the data when the user pulls down
                fetchTouristSites();
            }
        });

        editTextSearch = findViewById(R.id.search_bar);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Vous pouvez utiliser cette méthode si vous avez besoin de quelque chose avant que le texte ne change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Vous pouvez utiliser cette méthode si vous avez besoin de quelque chose après que le texte ait changé
            }
        });

    }

    private void fetchTouristSites() {
        Log.d("MainActivity", "fetchTouristSites started");
        ApiService apiService = ApiClient.getClient(BASE_URL).create(ApiService.class);
        Call<List<TouristSite>> call = apiService.getTouristSite();
        Log.d("MainActivity", "Network call initiated");
        call.enqueue(new Callback<List<TouristSite>>() {
            @Override
            public void onResponse(Call<List<TouristSite>> call, Response<List<TouristSite>> response) {
                Log.d("MainActivity", "Network call response received");
                if (response.isSuccessful() && response.body() != null) {
                    touristSiteList.clear();  // Clear the existing list to avoid duplicates
                    touristSiteList.addAll(response.body());  // Add the response data to the list
                    touristSiteList.addAll(response.body());  // Add the response data to the list
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("MainActivity", "Response error: " + response.message());
                    showError("Server error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TouristSite>> call, Throwable t) {
                Log.e("MainActivity", "Network error: ", t);
                showError("Network error: " + t.getMessage());
            }
        });
    }

    private void filter(String text) {
        List<TouristSite> filteredList = new ArrayList<>();
        for (TouristSite site : touristSiteList) {
            if (site.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(site);
            }
        }
        adapter = new TouristSiteAdapter(filteredList, this);
        recyclerView.setAdapter(adapter);
    }


    private void showError(String message) {
        // Show a Toast or Dialog with the error message
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
