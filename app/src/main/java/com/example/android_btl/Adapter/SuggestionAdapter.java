package com.example.android_btl.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.Model.Item;
import com.example.android_btl.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {

    private final Context context;
    private final List<Item> suggestions;

    public SuggestionAdapter(Context context, List<Item> suggestions) {
        this.context = context;
        this.suggestions = suggestions;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_suggestion, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        Item suggestion = suggestions.get(position);
        holder.suggestionName.setText(suggestion.getItem_name());
        holder.suggestionPrice.setText(formatCurrency(suggestion.getPrice()));
        holder.suggestionImage.setImageBitmap(BitmapFactory.decodeByteArray(suggestion.getImg(), 0, suggestion.getImg().length));
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public static class SuggestionViewHolder extends RecyclerView.ViewHolder {
        TextView suggestionName, suggestionPrice;
        ImageView suggestionImage;

        public SuggestionViewHolder(View itemView) {
            super(itemView);
            suggestionName = itemView.findViewById(R.id.suggestionName);
            suggestionPrice = itemView.findViewById(R.id.suggestionPrice);
            suggestionImage = itemView.findViewById(R.id.suggestionImage);
        }
    }
    private String formatCurrency(int amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return format.format(amount);
    }
}