package com.example.parking.ui.TopUpAnAccount;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.parking.R;
import com.example.parking.ui.About.AboutViewModel;
import com.example.parking.utility.AccountHolder;

public class TopUpAnAccountFragment extends Fragment {

    private TopUpAnAccountViewModel viewModel;
    private TextView textView;
    private EditText paymentView;
    private Button payBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(TopUpAnAccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top_up_an_account, container, false);

        //view searching
        textView = root.findViewById(R.id.top_up_an_account_under_payment_text);
        paymentView = root.findViewById(R.id.top_up_an_account_payment);
        payBtn = root.findViewById(R.id.top_up_an_account_pay_button);

        //text set upping
        paymentView.setHint("0");
        viewModel.update(AccountHolder.account.mBalance);
        paymentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0) viewModel.update(Integer.parseInt(s.toString()) + AccountHolder.account.mBalance);
                else viewModel.update(AccountHolder.account.mBalance);
            }
        });

        //button set up
        payBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AccountHolder.account.mBalance += Integer.parseInt(paymentView.getText().toString());
                AccountHolder.saveData(getActivity().getApplication());
            }
        });

        //live data updating
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}
