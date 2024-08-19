package app.rafit.madatour.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.rafit.madatour.R;
import app.rafit.madatour.ui.LoginActivity;
import app.rafit.madatour.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView textViewRegister = view.findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(v -> {
            ((LoginActivity) getActivity()).showRegisterFragment();
        });

        TextView textViewForgotPassword = view.findViewById(R.id.textViewForgotPassword);
        textViewForgotPassword.setOnClickListener(v -> {
            ((LoginActivity) getActivity()).showForgotPasswordFragment();
        });

        // Initialize buttonLogin after inflating the layout
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        // Set up the click listener for the button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use getActivity() as the context when starting a new activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
