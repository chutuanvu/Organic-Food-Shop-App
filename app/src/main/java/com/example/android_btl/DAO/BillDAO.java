package com.example.android_btl.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android_btl.Database.DatabaseHelper;
import com.example.android_btl.Model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDAO extends DatabaseHelper {

    public BillDAO(@Nullable Context context) {
        super(context);
    }

    public void addBill(Bill bill) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("bill_id", bill.getBill_id());
            values.put("user_id", bill.getUser_id());
            database.insert("bill", null, values);
        } catch (Exception e) {
            Log.e("addBill", "Something went wrong ", e);
        }
    }

    public void updateBill(Bill bill) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("user_id", bill.getUser_id());
            database.update("bill", values, "bill_id = ?", new String[]{bill.getBill_id()});
        } catch (Exception e) {
            Log.e("updateBill", "Something went wrong ", e);
        }
    }

    public void deleteBill(String billId) {
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            database.delete("bill", "bill_id = ?", new String[]{billId});
        } catch (Exception e) {
            Log.e("deleteBill", "Something went wrong ", e);
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> billList = new ArrayList<>();
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT * FROM bill";
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Bill bill = new Bill(
                            cursor.getString(cursor.getColumnIndexOrThrow("bill_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("user_id"))
                    );
                    billList.add(bill);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("getAllBills", "Something went wrong ", e);
        }
        return billList;
    }

    public Bill getBillById(String billId) {
        try (SQLiteDatabase database = this.getReadableDatabase()) {
            String query = "SELECT * FROM bill WHERE bill_id=?";
            Cursor cursor = database.rawQuery(query, new String[]{billId});
            if (cursor != null && cursor.moveToFirst()) {
                Bill bill = new Bill(
                        cursor.getString(cursor.getColumnIndexOrThrow("bill_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("user_id"))
                );
                cursor.close();
                return bill;
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("getBillById", "Something went wrong ", e);
        }
        return null;
    }
}