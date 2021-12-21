package com.example.parking.ui.buy_place;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.parking.R;

public class BuyPlaceFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.f_buy_place, container, false);
        //TODO: stop changing text
        //TODO: handle phone number pres by syscall
        final TextView phone = root.findViewById(R.id.to_buy_phone_number);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(String.format("tel:%s", phone.getText().toString())));
                startActivity(callIntent);
            }
        });
        return root;
    }

}
