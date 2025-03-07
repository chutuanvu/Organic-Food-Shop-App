package com.example.android_btl.Model;

public class Category {
    private String category_id;
    private String category_name;
    private String description;
    private String created_time;
    private String updated_time;

    public Category(String category_id, String category_name, String description, String created_time, String updated_time) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.description = description;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Override
    public String toString() {
        return category_name;
    }
}