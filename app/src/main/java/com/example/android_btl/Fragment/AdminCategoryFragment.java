package com.example.android_btl.Fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_btl.Adapter.AdminCategoryAdapter;
import com.example.android_btl.Adapter.AdminCategoryAdapter;
import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.DAO.CategoryDAO;
import com.example.android_btl.Model.Category;
import com.example.android_btl.Model.Category;
import com.example.android_btl.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AdminCategoryFragment extends Fragment {
    private List<Category> categoryList;
    private MaterialButton btnAdd, btnUpdate, btnClean;
    private TextInputEditText edtName, edtDescription;
    private RecyclerView rvCategory;
    private String id;
    private AdminCategoryAdapter adapter;
    private CategoryDAO categoryDAO;

    public AdminCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDAO = new CategoryDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_category, container, false);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnClean = view.findViewById(R.id.btnClean);

        rvCategory = view.findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        loadCategoryList();

        edtName = view.findViewById(R.id.edtName);
        edtDescription = view.findViewById(R.id.edtDescription);

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if (categoryDAO.checkCategoryName(name)) {
                Toast.makeText(getContext(), "Tên danh mục đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
            Category category = new Category(
                    UUID.randomUUID().toString(),
                    name,
                    description,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()),
                    null
            );
            categoryDAO.addCategory(category);
            Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
            loadCategoryList();
        });
        btnClean.setOnClickListener(v -> {
            edtDescription.setText("");
            edtName.setText("");
        });
        btnUpdate.setOnClickListener(v -> {
            String description = edtDescription.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            Category updatedCategory = new Category(
                    id,
                    name,
                    description,
                    categoryDAO.getCategoryById(id).getCreated_time(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date())
            );
            categoryDAO.updateCategory(updatedCategory);
            Toast.makeText(getContext(), "Cập nhật danh mục thành công", Toast.LENGTH_SHORT).show();
            loadCategoryList();
        });
        return view;
    }

    private void loadCategoryList() {
        Cursor cursor = categoryDAO.getAllCategory();
        categoryList = categoryDAO.getCategorysFromCursor(cursor);
        if (adapter == null) {
            adapter = new AdminCategoryAdapter(getContext(), categoryList, new AdminCategoryAdapter.OnCategoryClickListener() {
                @Override
                public void onCategoryClick(Category category) {
                    id = category.getCategory_id();
                    edtName.setText(category.getCategory_name());
                    edtDescription.setText(category.getDescription());
                }
            }, categoryId -> {
                deleteCategory(categoryId);
            });
            rvCategory.setAdapter(adapter);
        } else {
            adapter.updateCategoryList(categoryList);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void deleteCategory(String id) {
        categoryDAO.deleteCategory(id);
        loadCategoryList();
        Toast.makeText(getContext(), "Đã xóa danh mục", Toast.LENGTH_SHORT).show();
    }

}