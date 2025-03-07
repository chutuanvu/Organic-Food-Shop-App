package com.example.android_btl.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_btl.R;
import com.example.android_btl.UserHomeActivity;
import com.google.android.material.button.MaterialButton;

public class AdminSettingFragment extends Fragment {

    private MaterialButton btnLogout;

    public AdminSettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_setting, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserHomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        return view;
    }
}