package com.example.ocs.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.complientsystem.C2668R;

public class TermsAndCondition extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(C2668R.layout.fragment_terms, container, false);
        ((TextView) root.findViewById(C2668R.C2671id.text_terms)).setText("Terms Page...!");
        return root;
    }
}
