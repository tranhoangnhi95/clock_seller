package com.example.immortal.clock_seller.Model;

import java.io.Serializable;

public class SoldClock implements Serializable{
    private String user;
    private int img;
    private String name;
    private Integer price;
    private String detail;
    private String date;
    private String dateOfPay;
    private int quantity;

    public SoldClock() {
    }

    public String getUser() {
        return user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateOfPay() {
        return dateOfPay;
    }

    public void setDateOfPay(String dateOfPay) {
        this.dateOfPay = dateOfPay;
    }
}
