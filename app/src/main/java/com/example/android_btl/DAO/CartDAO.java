package com.example.android_btl.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android_btl.Database.DatabaseHelper;
import com.example.android_btl.Model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartDAO extends DatabaseHelper {
    private SQLiteDatabase database;

    public CartDAO(Context context) {
        super(context);
    }

    public void addItemToCart(Cart cart) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cart_id", cart.getCart_id());
        values.put("user_id", cart.getUser_id());
        values.put("item_id", cart.getItem_id());
        values.put("quantity", cart.getQuantity());
        values.put("created_time", cart.getCreated_time());
        database.insert("cart", null, values);
    }

    public void updateCart(Cart cart) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", cart.getQuantity());
        values.put("updated_time", cart.getUpdated_time());
        database.update("cart", values, "cart_id=?", new String[]{cart.getCart_id()});
    }

    public void deleteCartItem(String id) {
        database = this.getWritableDatabase();
        database.delete("cart", "cart_id=?", new String[]{id});
        database.close();
    }

    public Cursor getAllCartItems() {
        database = this.getWritableDatabase();
        String query = "SELECT * FROM cart";
        return database.rawQuery(query, null);
    }

    public boolean checkItem(String id) {
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT 1 FROM cart WHERE item_id=?";
            String[] selectionArgs = {id};
            Cursor cursor = database.rawQuery(query, selectionArgs);
            boolean exists = cursor.moveToFirst();
            cursor.close();
            return exists;
        } catch (Exception e) {
            Log.e("checkItem", "Something went wrong ", e);
        }
        return false;
    }
    public Cursor getCartByUserId(String userId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM cart WHERE user_id=?";
        String[] selectionArgs = {userId};
        return database.rawQuery(query, selectionArgs);
    }
    public List<Cart> getCartsFromCursor(Cursor cursor) {
        List<Cart> cartList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String cartId = cursor.getString(cursor.getColumnIndexOrThrow("cart_id"));
                String userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                String itemId = cursor.getString(cursor.getColumnIndexOrThrow("item_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                String createdTime = cursor.getString(cursor.getColumnIndexOrThrow("created_time"));
                String updatedTime = cursor.getString(cursor.getColumnIndexOrThrow("updated_time"));
                Cart cart = new Cart(cartId, userId, itemId, quantity, createdTime,updatedTime);
                cartList.add(cart);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cartList;
    }
}