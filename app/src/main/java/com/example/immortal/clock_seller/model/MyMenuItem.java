package com.example.immortal.clock_seller.model;

import java.io.Serializable;

public class MyMenuItem implements Serializable{
    private String Image; //link hình ảnh
    private String Name; //tên nhãn hàng

    public MyMenuItem() {
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
