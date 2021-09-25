package com.example.mad.models;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CartItem {
    private Item item;
    private int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return getQuantity() == cartItem.getQuantity() &&
                getItem().equals(cartItem.getItem());
    }

    @BindingAdapter("android.setVal")
    public static void getselectedSpinnerValue(Spinner spinner, int quantity){
        spinner.setSelection(quantity -1, true);

    }
    public static DiffUtil.ItemCallback<CartItem> itemCallback = new DiffUtil.ItemCallback<CartItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull CartItem oldItem, @NonNull @NotNull CartItem newItem) {
            //return oldItem.getItem().equals(newItem.getItem());
            return oldItem.getQuantity() == newItem.getQuantity();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull CartItem oldItem, @NonNull @NotNull CartItem newItem) {
            return oldItem.equals(newItem);
        }
    };
}
