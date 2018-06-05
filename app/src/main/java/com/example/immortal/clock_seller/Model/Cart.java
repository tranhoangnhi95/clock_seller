package com.example.immortal.clock_seller.Model;

import java.io.Serializable;

public class Cart implements Serializable{
    private String name;
    private Integer total;
    private int image;
    private int quantity;

    public Cart(String name, Integer total, int image, int quantity) {
        this.name = name;
        this.total = total;
        this.image = image;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
