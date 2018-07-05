package com.example.immortal.clock_seller.model;

import java.io.Serializable;

public class Model implements Serializable {

    private String name, b_name, detail, image; //tên mẫu, tên nhãn hàng, chi tiết, link hình ảnh
    private Integer price, quantity, sold; //giá, số lượng, số lượng đã bán

    public Model() {

    }

    public Model(String name, String b_name, String detail, String image, Integer price, Integer quantity, Integer sold) {
        this.name = name;
        this.b_name = b_name;
        this.detail = detail;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.sold = sold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }
}
