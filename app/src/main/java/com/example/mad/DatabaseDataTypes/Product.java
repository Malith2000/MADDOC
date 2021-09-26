package com.example.mad.DatabaseDataTypes;

import android.graphics.Bitmap;

import java.util.Objects;

public class Product extends FoodItem{
    private String id;
    private Bitmap image;

    public Product(String id, Bitmap image) {
        this.id = id;
        this.image = image;
    }

    public Product(String name, String price, String id, Bitmap image) {
        super(name, price);
        this.id = id;
        this.image = image;
    }

    public Product(String name, String price, String id) {
        super(name, price);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, image);
    }

    @Override
    public String toString() {
        return "Product{" +
                super.toString()+
                "id=" + id +
                '}';
    }
}
