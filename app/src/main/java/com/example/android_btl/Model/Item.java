package com.example.android_btl.Model;

public class Item {
    private String item_id;
    private String item_name;
    private String description;
    private int price;
    private int quantity;
    private String category_id;
    private byte[] img;
    private String created_time;
    private String updated_time;

    public Item(String item_id, String item_name, String description, int price, int quantity, String category_id, byte[] img, String created_time, String updated_time) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category_id = category_id;
        this.img = img;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
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