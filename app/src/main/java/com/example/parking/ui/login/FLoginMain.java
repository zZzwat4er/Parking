package com.example.parking.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;


public class FLoginMain extends Fragment {

    private Button sin;
    private Button sup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.f_login_main, container, false);

        sin = root.findViewById(R.id.f_log_main_in);
        sup = root.findViewById(R.id.f_log_main_up);

        sin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_main_to_login_sign_in));
        sup.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_main_to_login_sign_up));

        return root;
    }

}
