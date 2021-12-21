package com.example.parking.ui.user_info.email_change;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parking.R;
import com.example.parking.utility.AccountHolder;

public class EmailChange extends Fragment {

    private TextView email;
    private Button chBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.f_user_info_email_change, container, false);

        email = root.findViewById(R.id.email_layout_text);
        chBtn = root.findViewById(R.id.email_move_on_button);

        chBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty()){
                    AccountHolder.account.mEmail = email.getText().toString();
                    AccountHolder.saveData(getActivity().getApplication());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                }
            }
        });

        email.setText(AccountHolder.account.mEmail);

        return root;
    }
}
