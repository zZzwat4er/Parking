package com.example.parking.ui.UserInfo.FIOChange;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.parking.MainActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.server_comunnication_api.HttpRequest;
import com.example.parking.utility.server_comunnication_api.JSONPars;
import com.example.parking.utility.server_comunnication_api.comAPI;

import java.math.BigInteger;
import java.security.MessageDigest;

public class FIOChange extends Fragment {

    private TextView firstname;
    private TextView secondname;
    private TextView lastname;
    private Button chBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_user_info_fio_change, container, false);

        firstname = root.findViewById(R.id.user_fio_edit_firstname_layout_text);
        secondname = root.findViewById(R.id.user_fio_edit_secondname_layout_text);
        lastname = root.findViewById(R.id.user_fio_edit_lastname_layout_text);
        chBtn = root.findViewById(R.id.fio_move_on_button);

        chBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()){
                    try {
                        comAPI.updateFIO(AccountHolder.email,
                                AccountHolder.passwordHush,
                                firstname.getText().toString(),
                                lastname.getText().toString(),
                                (secondname.getText().toString().isEmpty()? null:secondname.getText().toString()),
                                getActivity().getApplicationContext(),
                                new HttpRequest.Listener() {
                                    @Override
                                    public void onRespond(String respond) {
                                        AccountHolder.account = JSONPars.parseAccount(respond);
                                        if(AccountHolder.account != null) {
                                            AccountHolder.saveData(getActivity().getApplication());
                                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                                        }
                                    }
                                });
                    }catch (Exception e){}
                }
            }
        });

        firstname.setText(AccountHolder.account.mFirstName);
        secondname.setText(AccountHolder.account.mSecondName);
        lastname.setText(AccountHolder.account.mLastName);

        return root;
    }
}
