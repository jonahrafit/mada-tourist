package app.rafit.madatour.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.rafit.madatour.R;
import app.rafit.madatour.ui.fragment.ForgotPasswordFragment;
import app.rafit.madatour.ui.fragment.LoginFragment;
import app.rafit.madatour.ui.fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            Log.d("SAVE", savedInstanceState.toString());
        }

        // Afficher LoginFragment par défaut
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    public void showRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RegisterFragment())
                .addToBackStack(null) // Permet de revenir en arrière
                .commit();
    }

    public void showForgotPasswordFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ForgotPasswordFragment())
                .addToBackStack(null)
                .commit();
    }
}