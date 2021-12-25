package com.example.parking.ui.entrance_rules;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.parking.R;

public class FEntranceRules extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.f_entrance_rules, container, false);
        String text = getString(R.string.entrance_rules_text);
        TextView textView = root.findViewById(R.id.entrance_rules_text);
        textView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        return root;
    }

}
