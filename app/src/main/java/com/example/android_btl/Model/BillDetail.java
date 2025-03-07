package com.example.android_btl.Model;

public class BillDetail {
    private String bill_detail_id;
    private String bill_id;
    private String item_id;
    private int quantity;
    private int price;
    private String created_time;
    private String updated_time;

    public BillDetail(String bill_detail_id, String bill_id, String item_id, int quantity, int price, String created_time,String updated_time) {
        this.bill_detail_id = bill_detail_id;
        this.bill_id = bill_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.price = price;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public String getBill_detail_id() {
        return bill_detail_id;
    }

    public void setBill_detail_id(String bill_detail_id) {
        this.bill_detail_id = bill_detail_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}
