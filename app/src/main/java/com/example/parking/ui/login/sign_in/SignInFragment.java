package com.example.parking.ui.login.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parking.MainActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SignInFragment extends Fragment {

    EditText emailField;
    EditText passField;
    Button inBtn;

    private final String TAG = "login";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //TODO: fix crash on incorrect password input
        final View root = inflater.inflate(R.layout.fragment_login_sign_in, container, false);
        emailField = root.findViewById(R.id.login_email_field);
        passField = root.findViewById(R.id.login_password_field);

//        emailField.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override public void afterTextChanged(Editable s) {updateApprove();}
//        });
//        passField.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override public void afterTextChanged(Editable s) {updateApprove();}
//        });

//        root.findViewById(R.id.goto_register_button).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_login_sign_in_to_login_sign_up));

        inBtn = root.findViewById(R.id.sing_in_button);

        inBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, ((Boolean)StringChecker.isEmail(emailField.getText().toString())).toString());
                Log.d(TAG, ((Boolean)StringChecker.isPassword(passField.getText().toString())).toString());
                if(StringChecker.isEmail(emailField.getText().toString()) &&
                    StringChecker.isPassword(passField.getText().toString())) {
                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
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
                                        if (AccountHolder.account != null) {
                                            AccountHolder.passwordHush = passHash;
                                            AccountHolder.email = emailField.getText().toString();
                                            AccountHolder.saveData(getActivity().getApplication());
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            intent.putExtra("S", "login");
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            root.findViewById(R.id.sing_in_button).setEnabled(true);
                                            pb.setVisibility(View.GONE);
                                            passField.setText("");
                                        }
                                    }
                                });
                    } catch (Exception e) {
                    }
                }else{
                    // todo: actions on invalid login/password
                }
            }
        });

        return root;
    }
}
