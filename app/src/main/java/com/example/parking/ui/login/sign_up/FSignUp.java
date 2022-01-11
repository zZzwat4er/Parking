package com.example.parking.ui.login.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import java.util.regex.Pattern;

public class FSignUp extends Fragment{

    private EditText lastName;
    private EditText firstName;
    @Nullable private EditText secondName;
    private EditText email;
    private EditText phone;
    private EditText password;
    private SignUpVM vm;

    private String phoneRegEx = "\\+7|" +
            "\\+7\\s\\(\\d{1,3}\\)?|" +
            "\\+7\\s\\(\\d{3}\\)\\s\\d{1,3}|" +
            "\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{1,2}|" +
            "\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{1,2}";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.f_login_sign_up, container, false);

        lastName = root.findViewById(R.id.register_last_name_field);
        firstName = root.findViewById(R.id.register_first_name_field);
        secondName = root.findViewById(R.id.register_second_name_field);
        email = root.findViewById(R.id.register_email_field);
        phone = root.findViewById(R.id.register_phone_field);
        password = root.findViewById(R.id.register_password_field);

        lastName.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) lastName.setBackground(requireActivity().getDrawable(R.drawable.field_form));
            else if (lastName.getText().toString().isEmpty())
                lastName.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
        });
        firstName.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) firstName.setBackground(requireActivity().getDrawable(R.drawable.field_form));
            else if (firstName.getText().toString().isEmpty())
                firstName.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
        });
        email.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) email.setBackground(requireActivity().getDrawable(R.drawable.field_form));
            else if (!StringChecker.isEmail(email.getText().toString()))
                email.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
        });
        password.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) password.setBackground(requireActivity().getDrawable(R.drawable.field_form));
            else if (!StringChecker.isPassword(password.getText().toString()))
                password.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) phone.setText("+7");
                else if (phone.getText().toString().length() <= 3)phone.setText("");
                if (hasFocus) phone.setBackground(requireActivity().getDrawable(R.drawable.field_form));
                else if (!Pattern.matches("\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}", phone.getText().toString()))
                    phone.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
            }
        });
        vm = new ViewModelProvider(this).get(SignUpVM.class);
        vm.getOutPutCode().observe(requireActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case ERR:
                        Integer err = vm.getErrorCode();
                        break;
                    case SUC:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("S", "login");
                        startActivity(intent);
                        requireActivity().finish();
                    case NONE:
                    default:
                        break;
                }
            }
        });


        phone.addTextChangedListener(new TextWatcher() {
            int selStart = -1;
            String prev;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prev = s.toString();
                if(count > after)selStart = phone.getSelectionStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty() && !phone.hasFocus()) return;
                if(!phoneNumCheck(s.toString())) {
                    String cur = s.toString();
                    String change = phoneNumFormat(s.toString());
                    change = (change.equals(cur))? prev : change;
                    phone.setText(change);
                    return;
                }
                if(selStart != -1) try{phone.setSelection(selStart - 1);}catch (Exception e){phone.setSelection(s.length());}
                else phone.setSelection(s.length());
                selStart = -1;
            }
        });

        root.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!lastName.getText().toString().isEmpty() &&
                        !firstName.getText().toString().isEmpty() &&
                        StringChecker.isEmail(email.getText().toString()) &&
                        StringChecker.isPassword(password.getText().toString()) &&
                        Pattern.matches("\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}", phone.getText().toString())) {
                    AccountHolder.email = email.getText().toString();
                    final String passhash;
                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        md.update(password.getText().toString().getBytes());
                        passhash = new BigInteger(1, md.digest()).toString(16);
                    }catch (NoSuchAlgorithmException e){
                        return;
                    }
                    if(passhash != null)
                        vm.serverRequest(
                                requireActivity(),
                                email.getText().toString(),
                                passhash,
                                phone.getText().toString(),
                                firstName.getText().toString(),
                                lastName.getText().toString(),
                                secondName.getText().toString());
                }else {
                    if (lastName.getText().toString().isEmpty())
                        lastName.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
                    if (firstName.getText().toString().isEmpty())
                        firstName.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
                    if (!StringChecker.isEmail(email.getText().toString()))
                        email.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
                    if (!StringChecker.isPassword(password.getText().toString()))
                        password.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
                    if (!Pattern.matches("\\+7\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}", phone.getText().toString()))
                        phone.setBackground(requireActivity().getDrawable(R.drawable.wrong_field_form));
                }
            }
        });
        return root;
    }

    private boolean phoneNumCheck(CharSequence s){
        return Pattern.matches(phoneRegEx, s);
    }

    private String phoneNumFormat(String s){
        if(!Pattern.matches("^\\+7.*", s)) return "+7";
        s = s.replaceFirst("\\+7\\s?\\(?(\\d)?\\s?\\(?(\\d)?\\)?\\s?(\\d)?\\)?\\s?(\\d)?\\)?\\s?(\\d{0,2})?-?(\\d)?-?(\\d)?-?(\\d)?-?(\\d)?-?(\\d)?(.?)", "+7 ($1$2$3) $4$5-$6$7-$8$9");
        while(!Character.isDigit(s.charAt(s.length() - 1)))
            s = s.substring(0, s.length() - 1);
        return s;
    }
}