package com.example.shoppingapp.Models;

import java.io.Serializable;

public class Shop implements Serializable {

    private String name, category, cashback, address, image;

    public Shop() {
    }

    public Shop(String name, String category, String cashback, String address, String image) {
        this.name = name;
        this.category = category;
        this.cashback = cashback;
        this.address = address;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCashback() {
        return cashback;
    }

    public void setCashback(String cashback) {
        this.cashback = cashback;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
