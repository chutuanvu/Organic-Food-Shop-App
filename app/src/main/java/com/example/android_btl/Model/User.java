package com.example.android_btl.Model;

public class User {
    private String user_id;
    private String user_name;
    private String name;
    private String password;
    private String email;
    private byte[] img;
    private String role;
    private String created_time;
    private String updated_time;

    public User(String user_id, String user_name, String name, String password, String email, byte[] img, String role, String created_time, String updated_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.name = name;
        this.password = password;
        this.email = email;
        this.img = img;
        this.role = role;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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