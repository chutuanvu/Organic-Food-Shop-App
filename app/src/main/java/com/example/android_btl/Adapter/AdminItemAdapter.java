package com.example.android_btl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.Model.Item;
import com.example.android_btl.R;

import java.util.List;

public class AdminItemAdapter extends RecyclerView.Adapter<AdminItemAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private final Context context;
    private List<Item> itemList;
    private CategoryDAO categoryDAO;

    public void updateItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String id);
    }

    public AdminItemAdapter(Context context, List<Item> itemList, OnItemClickListener listener, OnDeleteClickListener deleteListener, CategoryDAO categoryDAO) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.categoryDAO = categoryDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_listview_item, parent, false);
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

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(i -> {
                if (i.getItemId() == R.id.action_delete) {
                    deleteListener.onDeleteClick(item.getItem_id());
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
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvDescription, tvQuantity ,tvCategory;
        ImageView imvImg;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCategory = view.findViewById(R.id.tvCategory);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            imvImg = view.findViewById(R.id.imvImg);
        }
    }
}