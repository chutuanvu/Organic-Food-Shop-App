package com.example.android_btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.Model.Category;
import com.example.android_btl.R;

import java.util.List;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.ViewHolder> {
    private final OnCategoryClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private Context context;
    private List<Category> categoryList;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String id);
    }

    public AdminCategoryAdapter(Context context, List<Category> categoryList, OnCategoryClickListener listener, OnDeleteClickListener deleteListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_listview_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if (category == null) {
            return;
        }
        holder.tvName.setText(category.getCategory_name());
        holder.tvDescription.setText(category.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_delete) {
                    deleteListener.onDeleteClick(category.getCategory_id());
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
        return categoryList.size();
    }
    public void updateCategoryList(List<Category> newCategoryList) {
        this.categoryList = newCategoryList;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvName;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}