package com.example.android_btl.Model;

public class Bill {
    private String bill_id;
    private String user_id;


    public Bill(String bill_id, String user_id) {
        this.bill_id = bill_id;
        this.user_id = user_id;

    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
