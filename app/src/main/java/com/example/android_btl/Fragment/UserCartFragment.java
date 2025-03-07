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

import com.example.android_btl.Adapter.UserCartAdapter;
import com.example.android_btl.DAO.CartDAO;
import com.example.android_btl.DAO.ItemDAO;
import com.example.android_btl.Model.Cart;
import com.example.android_btl.R;

import java.util.List;

public class UserCartFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserCartAdapter userCartAdapter;
    private String id;
    private ItemDAO itemDAO;
    private CartDAO cartDAO;

    public UserCartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDAO = new ItemDAO(getContext());
        cartDAO = new CartDAO(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_cart, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", getContext().MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "defaultUserid");
        recyclerView = view.findViewById(R.id.rvCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Cursor cursor = cartDAO.getCartByUserId(userid);
        List<Cart> cartList = cartDAO.getCartsFromCursor(cursor);
        userCartAdapter = new UserCartAdapter(
                getContext(),
                cart -> id = cart.getCart_id(),
                cartList,
                itemDAO,
                this::onQuantityChange,
                itemId -> deleteCart(id));
        recyclerView.setAdapter(userCartAdapter);
        return view;
    }

    private void onQuantityChange(Cart cart, int newQuantity) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void deleteCart(String id) {
        cartDAO.deleteCartItem(id);
        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
        load();
    }

    public void load() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", getContext().MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "defaultUserid");
        Cursor cursor = cartDAO.getCartByUserId(userid);
        List<Cart> updatedCartList = cartDAO.getCartsFromCursor(cursor);
        userCartAdapter.setCartList(updatedCartList);
        userCartAdapter.notifyDataSetChanged();
    }
}