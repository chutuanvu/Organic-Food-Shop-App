package com.example.android_btl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_btl.Model.User;
import com.example.android_btl.R;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {
    private final OnUserClickListener listener;
    private final OnDeleteClickListener deleteListener;
    private Context context;
    private List<User> userList;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String userId);
    }

    public AdminUserAdapter(Context context, List<User> userList, OnUserClickListener listener, OnDeleteClickListener deleteListener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_listview_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) {
            return;
        }

        holder.tvUserName.setText(user.getUser_name());
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText(user.getRole());
        byte[] imgData = user.getImg();
        if (imgData != null && imgData.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            if (holder.imvImg != null) {
                holder.imvImg.setImageBitmap(bitmap);
            }
        } else {
            if (holder.imvImg != null) {
                holder.imvImg.setImageResource(R.drawable.ic_default_profile);
            }
        }

        holder.itemView.setOnClickListener(v -> listener.onUserClick(user));
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_delete) {
                    deleteListener.onDeleteClick(user.getUser_id());
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
        return userList.size();
    }

    public void updateUserList(List<User> newUserList) {
        this.userList = newUserList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvName, tvEmail, tvRole;
        ImageView imvImg;

        public ViewHolder(View view) {
            super(view);
            tvUserName = view.findViewById(R.id.tvUserName);
            tvName = view.findViewById(R.id.tvName);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvRole = view.findViewById(R.id.tvRole);
            imvImg = view.findViewById(R.id.imvImg);
        }
    }
}