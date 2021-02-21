package com.example.parking.ui.Login.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.MainActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SignInFragment extends Fragment {

    EditText emailField;
    EditText passField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_login_sign_in, container, false);
        emailField = root.findViewById(R.id.login_email_field);
        passField = root.findViewById(R.id.login_password_field);

        root.findViewById(R.id.goto_register_button).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_login_sign_in_to_login_sign_up));

        root.findViewById(R.id.sing_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(passField.getText().toString().getBytes());
                    final String passHash = new BigInteger(1, md.digest()).toString(16);

                    final ProgressBar pb = root.findViewById(R.id.progressBar);
                    root.findViewById(R.id.sing_in_button).setEnabled(false);
                    pb.setVisibility(View.VISIBLE);

                    comAPI.login(emailField.getText().toString(),
                            passHash,
                            getActivity().getApplicationContext(),
                            new HttpRequest.Listener() {
                                @Override
                                public void onRespond(String respond) {
                                    AccountHolder.account = JSONPars.parseAccount(respond);
                                    if(AccountHolder.account != null) {
                                        AccountHolder.passwordHush = passHash;
                                        AccountHolder.email = emailField.getText().toString();
                                        AccountHolder.saveData(getActivity().getApplication());
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra("S", "login");
                                        startActivity(intent);
                                        getActivity().finish();
                                    }else{
                                        root.findViewById(R.id.sing_in_button).setEnabled(true);
                                        pb.setVisibility(View.GONE);
                                        passField.setText("");
                                    }
                                }
                            });
                }catch (Exception e){}
            }
        });

        return root;
    }
}
