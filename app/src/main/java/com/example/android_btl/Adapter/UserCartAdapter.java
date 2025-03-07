package com.example.android_btl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android_btl.R;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.DAO.ItemDAO;
import com.example.android_btl.Model.Bill;
import com.example.android_btl.Model.BillDetail;
import com.example.android_btl.Model.Cart;
import com.example.android_btl.Model.Item;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHolder> {
    private final Context context;
    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private List<Cart> cartList;
    private final ItemDAO itemDAO;
    private final OnQuantityChangeListener quantityChangeListener;

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(Cart cart, int newQuantity);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(String id);
    }

    public interface OnItemClickListener {
        void onItemClick(Cart cart);
    }
    public UserCartAdapter(Context context, OnItemClickListener listener, List<Cart> cartList, ItemDAO itemDAO, OnQuantityChangeListener quantityChangeListener, OnDeleteClickListener deleteListener) {
        this.context = context;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.cartList = cartList;
        this.itemDAO = itemDAO;
        this.quantityChangeListener = quantityChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_listview_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        if (cart == null) return;
        Item item = itemDAO.getItemById(cart.getItem_id());
        if (item != null) {
            holder.tvName.setText(item.getItem_name());
            holder.tvPrice.setText(String.format("%s đ", item.getPrice()));

            byte[] imgData = item.getImg();
            if (imgData != null && imgData.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                holder.ivImg.setImageBitmap(bitmap);
            } else {
                holder.ivImg.setImageResource(R.drawable.ic_default_profile);
            }
        }

        holder.tvQuantity.setText(String.valueOf(cart.getQuantity()));

        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = cart.getQuantity();
            if (currentQuantity > 1) {
                int newQuantity = currentQuantity - 1;
                cart.setQuantity(newQuantity);
                holder.tvQuantity.setText(String.valueOf(newQuantity));
                quantityChangeListener.onQuantityChange(cart, newQuantity);
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = cart.getQuantity();
            int newQuantity = currentQuantity + 1;
            cart.setQuantity(newQuantity);
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            quantityChangeListener.onQuantityChange(cart, newQuantity);
        });
        holder.itemView.setOnClickListener(v -> listener.onItemClick(cart));

        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(i -> {
                if (i.getItemId() == R.id.action_delete) {
                    deleteListener.onDeleteClick(cart.getItem_id());
                    return true;
                }
                return false;
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvName, tvPrice, tvQuantity;
        TextView btnDecrease, btnIncrease;
        public ViewHolder(@NonNull View view) {
            super(view);
            ivImg = view.findViewById(R.id.ivImg);
            tvName = view.findViewById(R.id.tvName);
            tvPrice = view.findViewById(R.id.txtPrice);
            tvQuantity = view.findViewById(R.id.txtQuantity);
            btnDecrease = view.findViewById(R.id.btnDecrease);
            btnIncrease = view.findViewById(R.id.btnIncrease);
        }
    }
}
