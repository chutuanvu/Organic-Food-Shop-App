package com.example.android_btl.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android_btl.Database.DatabaseHelper;
import com.example.android_btl.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DatabaseHelper {

    public UserDAO(@Nullable Context context) {
        super(context);
    }

    public void addUser(User user) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("user_id", user.getUser_id());
            values.put("user_name", user.getUser_name());
            values.put("name", user.getName());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            values.put("role", user.getRole());
            values.put("created_time", user.getCreated_time());
            values.put("img", user.getImg());
            database.insert("user", null, values);
        } catch (Exception e) {
            Log.e("addUser", "Something went wrong ", e);
        }
    }
    public void updateUser(User user) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("user_name", user.getUser_name());
            values.put("name", user.getName());
            values.put("password", user.getPassword());
            values.put("email", user.getEmail());
            values.put("role", user.getRole());
            values.put("updated_time", user.getUpdated_time());
            database.update("user", values, "user_id = ?", new String[]{String.valueOf(user.getUser_id())});
        } catch (Exception e) {
            Log.e("updateUser", "Something went wrong ", e);
        }
    }

    public void deleteUser(String id) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            database.delete("user", "user_id = ?", new String[]{id});
        } catch (Exception e) {
            Log.e("deleteUser", "Something went wrong ", e);
        }
    }

    public Cursor getAllUsers() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM user";
        return database.rawQuery(query, null);
    }


    public User login(String username, String password) {
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT * FROM user WHERE user_name=? AND password=?";
            String[] selectionArgs = {username, password};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                User user = new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getBlob(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                );
                cursor.close();
                return user;
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("login", "Something went wrong ", e);
        }
        return null;
    }
    public List<User> getUsersFromCursor(Cursor cursor) {
        List<User> userList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String user_id = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                String user_name = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                String created_time = cursor.getString(cursor.getColumnIndexOrThrow("created_time"));
                String updated_time = cursor.getString(cursor.getColumnIndexOrThrow("updated_time"));

                User user = new User(user_id, user_name, name, password, email, img, role, created_time, updated_time);
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return userList;
    }

    public boolean checkUserName(String username) {
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT 1 FROM user WHERE user_name=?";
            String[] selectionArgs = {username};
            Cursor cursor = database.rawQuery(query, selectionArgs);
            boolean exists = cursor.moveToFirst();
            cursor.close();
            return exists;
        } catch (Exception e) {
            Log.e("checkUserName", "Something went wrong ", e);
        }
        return false;
    }
}