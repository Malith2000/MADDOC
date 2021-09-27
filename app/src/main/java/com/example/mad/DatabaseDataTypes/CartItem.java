package com.example.mad.DatabaseDataTypes;

import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {
    static final long serialVersionUID = 42L;

    private String name;
    private String imageId;
    private String price;
    private String category;
    private int quantity;

    public CartItem(String name, String imageId, String price, String category, int quantity) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(name, cartItem.name) && Objects.equals(imageId, cartItem.imageId) && Objects.equals(price, cartItem.price) && Objects.equals(category, cartItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageId, price, category);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", imageId='" + imageId + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
