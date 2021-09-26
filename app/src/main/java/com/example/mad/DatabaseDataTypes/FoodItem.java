package com.example.mad.DatabaseDataTypes;

import java.util.Objects;

public class FoodItem {
    private String Name;
    private String Price;

    public FoodItem(){}

    public FoodItem(String name, String price) {
        Name = name;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        return Objects.equals(Name, foodItem.Name) &&
                Objects.equals(Price, foodItem.Price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Price);
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                '}';
    }
}
