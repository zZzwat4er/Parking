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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.MainActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;
import com.example.parking.utility.server_comunnication_api.comAPI;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FSignIn extends Fragment {

    private EditText emailField;
    private EditText passField;
    private Button inBtn;
    private SignInVM vm;
    private ProgressBar pb;

    private final String TAG = "login";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.f_login_sign_in, container, false);
        emailField = root.findViewById(R.id.login_email_field);
        passField = root.findViewById(R.id.login_password_field);
        pb = root.findViewById(R.id.progressBar);
        inBtn = root.findViewById(R.id.sing_in_button);

        root.findViewById(R.id.login_restore_pass_btn).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_login_sign_in_to_login_pass_restore));
        vm = new ViewModelProvider(this).get(SignInVM.class);
        vm.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                Log.d("OBSERVER", "onChanged");
                switch (serverReqCodes){
                    case ERR:
                        inBtn.setEnabled(true);
                        pb.setVisibility(View.GONE);
                        passField.setText("");
                        break;
                    case SUC:
                        Log.d("OBSERVER", "onChanged: get suc result");
                        MessageDigest md = null;
                        try {
                            md = MessageDigest.getInstance("SHA-256");
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        md.update(passField.getText().toString().getBytes());
                        AccountHolder.passwordHush = new BigInteger(1, md.digest()).toString(16);
                        AccountHolder.email = emailField.getText().toString();
                        AccountHolder.saveData(getActivity().getApplication());
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("S", "login");
                        startActivity(intent);
                        getActivity().finish();
                    case NONE:
                    default:
                        break;
                }
            }
        });

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

                        inBtn.setEnabled(false);
                        pb.setVisibility(View.VISIBLE);
                        vm.serverRequest(getActivity(), emailField.getText().toString(), passHash);
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
