package com.example.immortal.clock_seller.Model;

import java.io.Serializable;

public class MyMenuItem implements Serializable{
    private int Image;
    private String Name;

    public MyMenuItem() {
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
