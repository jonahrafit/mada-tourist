package app.rafit.madatour.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.rafit.madatour.R;
import app.rafit.madatour.ui.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    TextView textViewHaveAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        textViewHaveAccount.setOnClickListener(v -> {
            ((LoginActivity) getActivity()).showForgotPasswordFragment();
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
}