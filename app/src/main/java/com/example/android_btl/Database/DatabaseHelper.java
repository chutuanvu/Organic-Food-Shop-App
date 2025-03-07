package com.example.android_btl.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "btl.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbUser = "CREATE TABLE IF NOT EXISTS user (" +
                "user_id TEXT PRIMARY KEY, " +
                "user_name TEXT, " +
                "name TEXT, " +
                "password TEXT, " +
                "email TEXT, " +
                "img BLOB, " +
                "role TEXT, " +
                "created_time TEXT, " +
                "updated_time TEXT);";
        sqLiteDatabase.execSQL(tbUser);

        String tbCategory = "CREATE TABLE IF NOT EXISTS category (" +
                "category_id TEXT PRIMARY KEY, " +
                "category_name TEXT, " +
                "description TEXT, " +
                "created_time TEXT, " +
                "updated_time TEXT);";
        sqLiteDatabase.execSQL(tbCategory);

        String tbItem = "CREATE TABLE IF NOT EXISTS item " +
                "(item_id TEXT PRIMARY KEY, " +
                "item_name TEXT, " +
                "description TEXT, " +
                "price INTEGER, " +
                "quantity INTEGER, " +
                "category_id TEXT, " +
                "img BLOB, " +
                "created_time TEXT, " +
                "updated_time TEXT, " +
                "FOREIGN KEY (category_id) REFERENCES category(category_id));";
        sqLiteDatabase.execSQL(tbItem);

        String tbBill = "CREATE TABLE IF NOT EXISTS bill (" +
                "bill_id TEXT PRIMARY KEY, " +
                "user_id TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(user_id));";
        sqLiteDatabase.execSQL(tbBill);

        String tbBillDetail = "CREATE TABLE IF NOT EXISTS bill_detail (" +
                "bill_detail_id TEXT PRIMARY KEY, " +
                "bill_id TEXT, " +
                "item_id TEXT, " +
                "quantity INTEGER, " +
                "price INTEGER, " +
                "created_time TEXT, " +
                "updated_time TEXT, " +
                "FOREIGN KEY (bill_id) REFERENCES bill_export(bill_id), " +
                "FOREIGN KEY (item_id) REFERENCES item(item_id));";
        sqLiteDatabase.execSQL(tbBillDetail);

        String tbCart = "CREATE TABLE IF NOT EXISTS cart (" +
                "cart_id TEXT PRIMARY KEY, " +
                "user_id TEXT, " +
                "item_id TEXT, " +
                "quantity INTEGER, " +
                "created_time TEXT, " +
                "updated_time TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES user(user_id), " +
                "FOREIGN KEY (item_id) REFERENCES item(item_id));";
        sqLiteDatabase.execSQL(tbCart);

        String insertUser1 = "INSERT INTO user (user_id, user_name, name, password, email, role, created_time, updated_time) " +
                "VALUES ('8cf8dc5e-3867-4194-bdbe-214c17941c63', 'admin', 'admin', '1', 'admin@gmail.com', 'admin', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertUser1);
        String insertUser2 = "INSERT INTO user (user_id, user_name, name, password, email, role, created_time, updated_time) " +
                "VALUES ('e457d15b-9cdf-455f-a51d-be2d7cfba514', 'user', 'user', '1', 'user@gmail.com', 'user', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertUser2);
        String insertCategory1 = "INSERT INTO category (category_id, category_name, description, created_time, updated_time) " +
                "VALUES ('d737ff56-7f6b-48cc-903b-3aca7d5060d2', 'Ngũ cốc và hạt', 'Gồm các loại ngũ cốc dinh dưỡng như gạo, yến mạch, bắp và các loại hạt như hạt điều, hạnh nhân, đậu xanh.', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertCategory1);
        String insertCategory2 = "INSERT INTO category (category_id, category_name, description, created_time, updated_time) " +
                "VALUES ('5221f97d-4e1a-449c-9ed2-355e9e40b398', 'Thực phẩm chăn nuôi', 'Bao gồm các sản phẩm từ chăn nuôi như trứng gà, trứng vịt, sữa bò tươi và các sản phẩm hữu cơ khác.', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertCategory2);
        String insertCategory3 = "INSERT INTO category (category_id, category_name, description, created_time, updated_time) " +
                "VALUES ('649232f9-eca4-48b9-967f-987f3b5ad956', 'Rau củ quả', 'Bao gồm các loại rau xanh, củ quả tươi như cải xanh, cà rốt, khoai tây, cà chua và nhiều loại khác được thu hoạch trực tiếp từ nông trại.', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertCategory3);
        String insertCategory4 = "INSERT INTO category (category_id, category_name, description, created_time, updated_time) " +
                "VALUES ('2dec4686-5a4e-4228-910f-95e39f32ac90', 'Trái cây', 'Cung cấp các loại trái cây tươi ngon theo mùa và trái cây nhập khẩu như xoài, cam, nho, táo và dưa hấu.', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertCategory4);
        String insertCategory5 = "INSERT INTO category (category_id, category_name, description, created_time, updated_time) " +
                "VALUES ('9ae21f15-88b7-40e8-82f0-bd90fb39a8b8', 'Sản phẩm chế biến', 'Các sản phẩm được chế biến từ nông sản như đồ khô, trái cây sấy, gia vị tự nhiên, và các loại bột.', '2025-01-12 12:12:12', '2025-01-12 12:12:12');";
        sqLiteDatabase.execSQL(insertCategory5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
