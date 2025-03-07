package com.example.android_btl.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android_btl.Database.DatabaseHelper;
import com.example.android_btl.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DatabaseHelper {
    private SQLiteDatabase database;
    public CategoryDAO(Context context) {
        super(context);
    }
    public void addCategory(Category category) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category_id", category.getCategory_id());
        values.put("category_name", category.getCategory_name());
        values.put("description", category.getDescription());
        values.put("created_time", category.getCreated_time());
        values.put("updated_time", category.getUpdated_time());
        database.insert("category", null, values);
        database.close();
    }
    public void updateCategory(Category category) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category_name", category.getCategory_name());
        values.put("description", category.getDescription());
        values.put("updated_time", category.getUpdated_time());
        database.update("category", values, "category_id=?", new String[]{category.getCategory_id()});
        database.close();
    }
    public void deleteCategory(String categoryId) {
        database = this.getWritableDatabase();
        database.delete("category", "category_id=?", new String[]{categoryId});
        database.close();
    }
    public Cursor getAllCategory() {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM category";
        return database.rawQuery(query, null);
    }

    public List<Category> getCategorysFromCursor(Cursor cursor) {
        List<Category> categoryList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category_id = cursor.getString(cursor.getColumnIndexOrThrow("category_id"));
                String category_name = cursor.getString(cursor.getColumnIndexOrThrow("category_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String created_time = cursor.getString(cursor.getColumnIndexOrThrow("created_time"));
                String updated_time = cursor.getString(cursor.getColumnIndexOrThrow("updated_time"));

                Category category = new Category(category_id, category_name, description, created_time, updated_time);
                categoryList.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return categoryList;
    }
    public Category getCategoryById(String id) {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM category WHERE category_id=?";
        String[] selectionArgs = {id};
        Cursor cursor = database.rawQuery(query, selectionArgs);
        Category category = null;
        if (cursor.moveToFirst()) {
            String category_id = cursor.getString(0);
            String category_name = cursor.getString(1);
            String description = cursor.getString(2);
            String created_time = cursor.getString(3);
            String updated_time = cursor.getString(4);
            category = new Category(category_id, category_name, description, created_time, updated_time);
        }
        cursor.close();
        return category;
    }

    public boolean checkCategoryName(String categoryName) {
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT 1 FROM category WHERE category_name=?";
            String[] selectionArgs = {categoryName};
            Cursor cursor = database.rawQuery(query, selectionArgs);
            boolean exists = cursor.moveToFirst();
            cursor.close();
            return exists;
        } catch (Exception e) {
            Log.e("checkCategoryName", "Something went wrong", e);
        }
        return false;
    }
}