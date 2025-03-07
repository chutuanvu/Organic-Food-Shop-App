package com.example.android_btl.Model;

public class Cart {
    private String cart_id;
    private String user_id;
    private String item_id;
    private int quantity;
    private String created_time;
    private String updated_time;

    public Cart(String cart_id, String user_id, String item_id, int quantity, String created_time, String updated_time) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
