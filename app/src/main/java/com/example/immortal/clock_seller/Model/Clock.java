package com.example.immortal.clock_seller.Model;

import java.io.Serializable;

public class Clock implements Serializable {
    private int img;
    private String name;
    private Integer price;
    private String detail;
    private String date;

    public Clock() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDes() {
        return detail;
    }

    public void setDes(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
