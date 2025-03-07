package com.example.android_btl.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android_btl.Database.DatabaseHelper;
import com.example.android_btl.Model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO extends DatabaseHelper {

    public BillDetailDAO(@Nullable Context context) {
        super(context);
    }

    public void addBillDetail(BillDetail billDetail) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("bill_detail_id", billDetail.getBill_detail_id());
            values.put("bill_id", billDetail.getBill_id());
            values.put("item_id", billDetail.getItem_id());
            values.put("quantity", billDetail.getQuantity());
            values.put("price", billDetail.getPrice());
            values.put("created_time", billDetail.getCreated_time());
            values.put("updated_time", billDetail.getUpdated_time());
            database.insert("bill_detail", null, values);
        } catch (Exception e) {
            Log.e("addBillDetail", "Something went wrong ", e);
        }
    }

    public void updateBillDetail(BillDetail billDetail) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("quantity", billDetail.getQuantity());
            values.put("price", billDetail.getPrice());
            values.put("updated_time", billDetail.getUpdated_time());
            database.update("bill_detail", values, "bill_detail_id = ?", new String[]{billDetail.getBill_detail_id()});
        } catch (Exception e) {
            Log.e("updateBillDetail", "Something went wrong ", e);
        }
    }

    public void deleteBillDetail(String billDetailId) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            database.delete("bill_detail", "bill_detail_id = ?", new String[]{billDetailId});
        } catch (Exception e) {
            Log.e("deleteBillDetail", "Something went wrong ", e);
        }
    }

    public List<BillDetail> getBillDetailsByBillId(String billId) {
        List<BillDetail> billDetailList = new ArrayList<>();
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT * FROM bill_detail WHERE bill_id=?";
            Cursor cursor = database.rawQuery(query, new String[]{billId});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    BillDetail billDetail = new BillDetail(
                            cursor.getString(cursor.getColumnIndexOrThrow("bill_detail_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("bill_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("item_id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                            cursor.getString(cursor.getColumnIndexOrThrow("created_time")),
                            cursor.getString(cursor.getColumnIndexOrThrow("updated_time"))
                    );
                    billDetailList.add(billDetail);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("getBillDetailsByBillId", "Something went wrong ", e);
        }
        return billDetailList;
    }

    public BillDetail getBillDetailById(String billDetailId) {
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT * FROM bill_detail WHERE bill_detail_id=?";
            Cursor cursor = database.rawQuery(query, new String[]{billDetailId});
            if (cursor != null && cursor.moveToFirst()) {
                BillDetail billDetail = new BillDetail(
                        cursor.getString(cursor.getColumnIndexOrThrow("bill_detail_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("bill_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("item_id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("created_time")),
                        cursor.getString(cursor.getColumnIndexOrThrow("updated_time"))
                );
                cursor.close();
                return billDetail;
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("getBillDetailById", "Something went wrong ", e);
        }
        return null;
    }
}
