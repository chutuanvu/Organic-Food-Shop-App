package com.example.android_btl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android_btl.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.Model.Item;

import java.util.List;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {
    private final OnAddToCartListener addToCartListener;
    private final Context context;
    private List<Item> itemList;
    private CategoryDAO categoryDAO;

    public interface OnAddToCartListener {
        void onAddToCart(Item item);
    }


    public UserItemAdapter(Context context, List<Item> itemList,
                           OnAddToCartListener addToCartListener, CategoryDAO categoryDAO) {
        this.context = context;
        this.itemList = itemList;
        this.addToCartListener = addToCartListener;
        this.categoryDAO = categoryDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_listview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        if (item == null) {
            return;
        }
        holder.tvName.setText(item.getItem_name());
        holder.tvPrice.setText(String.valueOf(item.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvDescription.setText(item.getDescription());
        holder.tvCategory.setText(categoryDAO.getCategoryById(item.getCategory_id()).getCategory_name());

        byte[] imgData = item.getImg();
        if (imgData != null && imgData.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            holder.imvImg.setImageBitmap(bitmap);
        } else {
            holder.imvImg.setImageResource(R.drawable.ic_default_profile);
        }

        holder.btnAddToCart.setOnClickListener(v -> {
            addToCartListener.onAddToCart(item);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvDescription, tvQuantity, tvCategory;
        ImageView imvImg;
        Button  btnAddToCart;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCategory = view.findViewById(R.id.tvCategory);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            imvImg = view.findViewById(R.id.imvImg);
            btnAddToCart = view.findViewById(R.id.btnAddToCart);
        }
    }
}