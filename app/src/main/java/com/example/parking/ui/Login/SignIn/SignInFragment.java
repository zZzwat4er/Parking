package com.example.parking.ui.Login.SignIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;

public class SignInFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login_sign_in, container, false);

        root.findViewById(R.id.goto_register_button).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_login_sign_in_to_login_sign_up));

        return root;
    }
}
