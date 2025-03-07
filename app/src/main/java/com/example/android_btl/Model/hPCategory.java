package com.example.android_btl.Model;

public class hPCategory {
    private String name;
    private int imageResId;

    public hPCategory(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
