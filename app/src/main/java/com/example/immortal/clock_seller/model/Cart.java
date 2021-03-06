package com.example.immortal.clock_seller.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private String name; //Tên mẫu sản phẩm
    private Integer price; //Giá mẫu sản phẩm
    private Integer total; //Tổng tiền sản phẩm
    private String image; //Chuỗi link hình ảnh
    private int quantity; //Số lượng mẫu trong giỏ hàng
    private int maxQuantity; //Số lượng tối đa có thể đặt mua
    private int sold; //Số lượng đã bán của mẫu sản phẩm


    public Cart() {
    }

    public Cart(String name, Integer price, Integer total, String image, int quantity, int maxQuantity, int sold) {
        this.name = name;
        this.price = price;
        this.total = total;
        this.image = image;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.sold = sold;
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

    public Integer getTotal() {
        return total;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
