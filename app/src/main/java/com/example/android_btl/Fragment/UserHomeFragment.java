package com.example.android_btl.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_btl.Adapter.ImageAdapter;
import com.example.android_btl.Adapter.SuggestionAdapter;
import com.example.android_btl.Adapter.hPCategoryAdapter;
import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.DAO.ItemDAO;
import com.example.android_btl.Model.Category;
import com.example.android_btl.Model.Item;
import com.example.android_btl.Model.hPCategory;
import com.example.android_btl.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private ItemDAO itemDAO;
    private RecyclerView rcCategory, rcSuggestion;
    private hPCategoryAdapter categoryAdapter;
    private SuggestionAdapter suggestionAdapter;
    private ArrayList<hPCategory> categoryList;
    private List<Item> suggestionList;
    private ImageAdapter adapter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable slideRunnable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDAO = new ItemDAO(getContext());
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        ArrayList<Integer> imageIds = new ArrayList<>(Arrays.asList(
                R.drawable.slide_1,
                R.drawable.slide_2,
                R.drawable.slide_3,
                R.drawable.slide_4
        ));
        adapter = new ImageAdapter(requireContext(), imageIds);
        viewPager.setAdapter(adapter);

        rcCategory = view.findViewById(R.id.rcCategory);
        categoryList = new ArrayList<>(Arrays.asList(
                new hPCategory("Trái cây", R.drawable.traicay),
                new hPCategory("Rau củ", R.drawable.raucu),
                new hPCategory("Ngũ cốc", R.drawable.ngucoc),
                new hPCategory("Nước uống", R.drawable.nuoc)

        ));

        categoryAdapter = new hPCategoryAdapter(getContext(), categoryList);
        rcCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcCategory.setAdapter(categoryAdapter);
        rcSuggestion = view.findViewById(R.id.rcSuggestion);

        byte[] img1 = convert(getContext(), R.drawable.apple);
        byte[] img2 = convert(getContext(), R.drawable.apple);
        byte[] img3 = convert(getContext(), R.drawable.apple);
        Cursor cursor = itemDAO.getAllItem();
        suggestionList = itemDAO.getItemsFromCursor(cursor);

        suggestionAdapter = new SuggestionAdapter(getContext(), suggestionList);
        rcSuggestion.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rcSuggestion.setAdapter(suggestionAdapter);
        slideRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = currentItem + 1;
                if (nextItem >= adapter.getItemCount()) {
                    nextItem = 0;
                }
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(slideRunnable, 3000);
        return view;
    }

    public byte[] convert(Context context, int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(slideRunnable);
    }
}
