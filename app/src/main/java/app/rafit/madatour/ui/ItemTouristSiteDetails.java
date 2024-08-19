package app.rafit.madatour.ui;

import app.rafit.madatour.R;
import app.rafit.madatour.api.ApiClient;
import app.rafit.madatour.api.ApiService;
import app.rafit.madatour.model.ImagePagerAdapter;
import app.rafit.madatour.model.TouristSite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

public class ItemTouristSiteDetails extends AppCompatActivity {
    private String BASE_URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BASE_URL = getApplicationContext().getString(R.string.api_base_url);
        setContentView(R.layout.activity_item_tourist_site_details);

        String itemId = getIntent().getStringExtra("ITEM_ID");

        fetchTouristSiteDetails(itemId);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Retourne à l'activité précédente
            }
        });
    }

    private void fetchTouristSiteDetails(String itemId) {
        ApiService apiService = ApiClient.getClient(BASE_URL).create(ApiService.class);
        Call<TouristSite> call = apiService.getTouristSiteById(itemId);  // Adjust the endpoint for a single item

        call.enqueue(new Callback<TouristSite>() {
            @Override
            public void onResponse(Call<TouristSite> call, Response<TouristSite> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TouristSite touristSite = response.body();

                    // Update UI with the retrieved data
                    updateUIWithTouristSiteDetails(touristSite);
                } else {
                    Log.e("ItemTouristSiteDetails", "Response error: " + response.message());
                    showError("Server error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TouristSite> call, Throwable t) {
                Log.e("ItemTouristSiteDetails", "Network error: ", t);
                showError("Network error: " + t.getMessage());
            }
        });
    }

    private void updateUIWithTouristSiteDetails(TouristSite site) {
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        TextView titleTextView = findViewById(R.id.titleTextView);

        titleTextView.setText(site.getName());
        descriptionTextView.setText(site.getDescription());
        locationTextView.setText(site.getLocation());

        // Configurer le RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, site.getImages());
        recyclerView.setAdapter(adapter);

        // Configurer la VideoView
        VideoView videoView = findViewById(R.id.videoView);
        // Configurer la VideoView
        ImageButton playButton = findViewById(R.id.playButton);
        TextView videoTiming = findViewById(R.id.videoTiming);


        videoView.setVideoURI(Uri.parse(site.getVideos().get(0)));
        videoView.start(); // Optionnel: pour démarrer la vidéo automatiquement
        Uri videoUri = Uri.parse(site.getVideos().get(0));
        videoView.setVideoURI(videoUri);

        // Démarrer la vidéo lorsque le bouton Play est cliqué
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(View.GONE); // Masquer le bouton Play
                videoView.start(); // Démarrer la vidéo
            }
        });

        // Mettre en pause/lecture lorsqu'on touche la vidéo
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (videoView.isPlaying()) {
                    videoView.pause(); // Mettre en pause la vidéo
                    playButton.setVisibility(View.VISIBLE); // Afficher le bouton Play
                } else {
                    playButton.setVisibility(View.GONE); // Masquer le bouton Play
                    videoView.start(); // Reprendre la lecture de la vidéo
                }
                return true;
            }
        });

        // Mettre à jour le timing de la vidéo
        Runnable updateTiming = new Runnable() {
            @Override
            public void run() {
                int currentPosition = videoView.getCurrentPosition() / 1000; // En secondes
                int duration = videoView.getDuration() / 1000; // En secondes
                videoTiming.setText(String.format("%02d:%02d / %02d:%02d",
                        currentPosition / 60, currentPosition % 60,
                        duration / 60, duration % 60));
                videoTiming.postDelayed(this, 1000); // Mettre à jour chaque seconde
            }
        };

        // Démarrer la mise à jour du timing lorsque la vidéo commence
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoTiming.post(updateTiming);
            }
        });

        // Réinitialiser le bouton Play lorsque la vidéo est terminée
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playButton.setVisibility(View.VISIBLE); // Afficher à nouveau le bouton Play
            }
        });

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

