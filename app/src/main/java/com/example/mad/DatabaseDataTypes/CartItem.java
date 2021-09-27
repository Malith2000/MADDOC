package com.example.mad.DatabaseDataTypes;

import java.util.Objects;

public class CartItem {
    private String name;
    private String imageId;
    private String price;

    public CartItem(String name, String imageId, String price) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
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

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", imageId='" + imageId + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(getName(), cartItem.getName()) &&
                Objects.equals(getImageId(), cartItem.getImageId()) &&
                Objects.equals(getPrice(), cartItem.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getImageId(), getPrice());
    }
}
