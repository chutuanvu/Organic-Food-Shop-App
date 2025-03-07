package com.example.android_btl.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.android_btl.Database.DatabaseHelper;
import com.example.android_btl.Model.Item;
import com.example.android_btl.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DatabaseHelper {

    private SQLiteDatabase database;

    public ItemDAO(Context context) {
        super(context);
    }

    public void addItem(Item item) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_id", item.getItem_id());
        values.put("item_name", item.getItem_name());
        values.put("description", item.getDescription());
        values.put("price", item.getPrice());
        values.put("quantity", item.getQuantity());
        values.put("category_id", item.getCategory_id());
        values.put("img", item.getImg());
        values.put("created_time", item.getCreated_time());
        database.insert("item", null, values);
        database.close();
    }

    public void updateItem(Item item) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", item.getItem_name());
        values.put("description", item.getDescription());
        values.put("price", item.getPrice());
        values.put("quantity", item.getQuantity());
        values.put("category_id", item.getCategory_id());
        values.put("img", item.getImg());
        values.put("updated_time", item.getUpdated_time());
        database.update("item", values, "item_id=?", new String[]{item.getItem_id()});
        database.close();
    }

    public void deleteItem(String itemId) {
        database = this.getWritableDatabase();
        database.delete("item", "item_id=?", new String[]{itemId});
        database.close();
    }

    public Cursor getAllItem() {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM item";
        return database.rawQuery(query, null);
    }

    public List<Item> getItemsFromCursor(Cursor cursor) {
        List<Item> itemList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String item_id = cursor.getString(cursor.getColumnIndexOrThrow("item_id"));
                String item_name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int price = 0;
                int quantity = 0;
                try {
                    price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("price")));
                    quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
                } catch (NumberFormatException e) {
                    Log.e("getItemsFromCursor", "Something went wrong " + e.getMessage());
                }
                String category_id = cursor.getString(cursor.getColumnIndexOrThrow("category_id"));
                byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
                String created_time = cursor.getString(cursor.getColumnIndexOrThrow("created_time"));
                String updated_time = cursor.getString(cursor.getColumnIndexOrThrow("updated_time"));

                Item item = new Item(item_id, item_name, description, price, quantity,category_id, img, created_time, updated_time);
                itemList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return itemList;
    }
    public Item getItemById(String itemId) {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM item WHERE item_id=?";
        String[] selectionArgs = {itemId};
        Cursor cursor = database.rawQuery(query, selectionArgs);
        Item item = null;
        if (cursor.moveToFirst()) {
            String item_id = cursor.getString(0);
            String item_name = cursor.getString(1);
            String description = cursor.getString(2);
            int price = cursor.getInt(3);
            int quantity = cursor.getInt(4);
            String category_id = cursor.getString(5);
            byte[] img = cursor.getBlob(6);
            String created_time = cursor.getString(7);
            String updated_time = cursor.getString(8);
            item = new Item(item_id, item_name, description, price, quantity, category_id, img, created_time, updated_time);
        }
        cursor.close();
        return item;
    }
}