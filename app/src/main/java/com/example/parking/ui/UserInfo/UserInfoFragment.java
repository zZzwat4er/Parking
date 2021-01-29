package com.example.parking.ui.UserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.MainActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;

public class UserInfoFragment extends Fragment {

    private UserInfoViewModel viewModel;
    private LinearLayout userFIO;
    private LinearLayout userPhone;
    private LinearLayout userEmail;
    private TextView exitButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(UserInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_info, container, false);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        userFIO = root.findViewById(R.id.user_fio_edit_lastname_layout);
        userPhone = root.findViewById(R.id.user_fio_edit_firstname_layout);
        userEmail = root.findViewById(R.id.user_fio_edit_secondname_layout);
        exitButton = root.findViewById(R.id.user_info_exit_button);

        TextView setUpper;
        setUpper = (TextView) userFIO.getChildAt(1);
        setUpper.setText(AccountHolder.account.getFullName());
        setUpper = (TextView) userPhone.getChildAt(1);
        setUpper.setText(AccountHolder.account.mPhone);
        setUpper = (TextView) userEmail.getChildAt(1);
        setUpper.setText(AccountHolder.account.mEmail);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountHolder.account = null;
                AccountHolder.passwordHush = null;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                AccountHolder.saveData(getActivity().getApplication());
                getActivity().finish();
            }
        });

        userFIO.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_user_info_to_nav_user_info_fio_change, null));
        userEmail.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_nav_user_info_to_nav_user_info_email_change, null));

        return root;
    }

}
