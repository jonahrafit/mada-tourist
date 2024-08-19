package app.rafit.madatour.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;

import app.rafit.madatour.R;

public class MainMenuActivity extends AppCompatActivity {

    private ImageView backgroundImage;
    private int[] images = {R.drawable.background_image, R.drawable.background_image2, R.drawable.background_image3};
    private int currentIndex = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        backgroundImage = findViewById(R.id.backgroundImage);
        startSlideshow();

        Button exploreButton = findViewById(R.id.explore_button);
        Button loginButton = findViewById(R.id.login_button);

        exploreButton.setOnClickListener(v -> {
            // Ajouter l'intention pour explorer sans se connecter
            Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            // Ajouter l'intention pour se connecter ou s'inscrire
            Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private void startSlideshow() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backgroundImage.setImageResource(images[currentIndex]);
                currentIndex = (currentIndex + 1) % images.length;
                handler.postDelayed(this, 3000); // Change image every 3 seconds
            }
        }, 6000);
    }
}