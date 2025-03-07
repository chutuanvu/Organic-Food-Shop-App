package com.example.android_btl.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_btl.Adapter.ImageAdapter;
import com.example.android_btl.R;

import java.util.ArrayList;
import java.util.Arrays;

public class UserHelpFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_help, container, false);
        return  view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
