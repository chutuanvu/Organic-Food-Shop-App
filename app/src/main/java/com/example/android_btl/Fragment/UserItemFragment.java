package com.example.android_btl.Fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.Adapter.UserItemAdapter;
import com.example.android_btl.DAO.CartDAO;
import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.DAO.ItemDAO;
import com.example.android_btl.Model.Cart;
import com.example.android_btl.Model.Item;
import com.example.android_btl.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UserItemFragment extends Fragment {
    private ItemDAO itemDAO;
    private CartDAO cartDAO;
    private List<Item> itemList;
    private CategoryDAO categoryDAO;
    private UserItemAdapter adapter;

    public UserItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDAO = new ItemDAO(getContext());
        cartDAO = new CartDAO(getContext());
        categoryDAO = new CategoryDAO(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_product, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", getContext().MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "defaultUserid");
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadItemList();
        if (itemList != null && !itemList.isEmpty()) {
            adapter = new UserItemAdapter(getContext(), itemList, item -> addItemToCart(item, userid), categoryDAO);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void loadItemList() {
        Cursor cursor = itemDAO.getAllItem();
        if (cursor != null) {
            itemList = itemDAO.getItemsFromCursor(cursor);
            cursor.close();
        } else {
            itemList = null;
        }
    }

    private void addItemToCart(Item item, String id) {
        Cart cart = new Cart(
                UUID.randomUUID().toString(),
                id,
                item.getItem_id(),
                item.getQuantity(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()),
                null
        );
        if (cartDAO.checkItem(item.getItem_id()))
        {
            Toast.makeText(getContext(), "Sản phẩm đã có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
            return;
        }
        cartDAO.addItemToCart(cart);
        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}