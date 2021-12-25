package com.example.parking.ui.user_rules;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.parking.R;

public class FUserRules extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.f_user_rules, container, false);
        String userRuleText = getString(R.string.user_rules_text);
        TextView textView = root.findViewById(R.id.user_rules_text);
        textView.setText(Html.fromHtml(userRuleText), TextView.BufferType.SPANNABLE);
        return root;
    }

}
