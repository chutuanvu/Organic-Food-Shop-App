package com.example.android_btl.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android_btl.Fragment.AdminCategoryFragment;
import com.example.android_btl.Fragment.AdminItemFragment;
import com.example.android_btl.Fragment.AdminSettingFragment;
import com.example.android_btl.Fragment.AdminUserFragment;

public class viewPage2Adapter extends FragmentStateAdapter {

    public viewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminCategoryFragment();
            case 1:
                return new AdminItemFragment();
            case 2:
                return new AdminUserFragment();
            case 3:
                return new AdminSettingFragment();
            default:
                return new AdminCategoryFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
