package com.example.immortal.clock_seller.Model;

import java.io.Serializable;

public class MyMenuItem implements Serializable{
    private String Image;
    private String Name;

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
