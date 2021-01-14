package com.example.parking.ui.UserInfo.FIOChange;

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

import com.example.parking.R;
import com.example.parking.utility.AccountHolder;

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
                    AccountHolder.account.mFirstName = firstname.getText().toString();
                    AccountHolder.account.mLastName = lastname.getText().toString();
                    AccountHolder.account.mSecondName = (secondname.getText().toString().isEmpty())?
                        null : secondname.getText().toString();
                    AccountHolder.saveData(getActivity().getApplication());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                }
            }
        });

        firstname.setText(AccountHolder.account.mFirstName);
        secondname.setText(AccountHolder.account.mSecondName);
        lastname.setText(AccountHolder.account.mLastName);

        return root;
    }
}
