package app.rafit.madatour.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.List;
import android.util.Log;

import app.rafit.madatour.R;
import app.rafit.madatour.ui.ItemTouristSiteDetails;

public class TouristSiteAdapter extends RecyclerView.Adapter<TouristSiteAdapter.ViewHolder> {

    private List<TouristSite> touristSites;
    private Context context;

    public TouristSiteAdapter(List<TouristSite> touristSites, Context context) {
        this.touristSites = touristSites;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tourist_site, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TouristSite site = touristSites.get(position);
        holder.nameTextView.setText(site.getName());
        // holder.descriptionTextView.setText(site.getDescription());

        List<String> imageUrls = site.getImages();
        Log.d("ImageUrls", "Image URLs for position " + position + ": " + imageUrls);

        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(context, imageUrls);
        holder.viewPager.setAdapter(imagePagerAdapter);

        holder.navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clické ny bouton");
                System.out.println(site);
                Intent intent = new Intent(context, ItemTouristSiteDetails.class);
                intent.putExtra("ITEM_ID", site.getId()); // Assurez-vous que `getId` existe dans TouristSite
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return touristSites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public TextView locationTextView;
        public ViewPager2 viewPager;
        private Button navigateButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            // descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            viewPager = itemView.findViewById(R.id.viewPager);
            navigateButton = itemView.findViewById(R.id.navigateButton);
        }
    }

    public void updateList(List<TouristSite> newList) {
        touristSites = newList;
        notifyDataSetChanged();
    }
}
