package com.example.android_btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.Model.Category;
import com.example.android_btl.Model.hPCategory;
import com.example.android_btl.R;

import java.util.ArrayList;

public class hPCategoryAdapter extends RecyclerView.Adapter<hPCategoryAdapter.hPCategoryViewHolder> {

    private final Context context;
    private final ArrayList<hPCategory> categories;

    public hPCategoryAdapter(Context context, ArrayList<hPCategory> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public hPCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new hPCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hPCategoryViewHolder holder, int position) {
        hPCategory category = categories.get(position);
        holder.categoryName.setText(category.getName());
        holder.categoryImage.setImageResource(category.getImageResId());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class hPCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public hPCategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}