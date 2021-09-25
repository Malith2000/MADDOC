package com.example.mad.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mad.models.CartItem;
import com.example.mad.models.Item;
import com.example.mad.repositories.CartRepo;
import com.example.mad.repositories.MenuRepo;

import java.util.List;

public class MenuViewModel extends ViewModel {

    MenuRepo menuRepo = new MenuRepo();
    CartRepo cartRepo = new CartRepo();

    MutableLiveData<Item> mutableItem = new MutableLiveData<>();

    public LiveData<List<Item>> getItem(){
        return menuRepo.getItem();

    }
    public void setItem(Item item){
        mutableItem.setValue(item);

    }
    public LiveData<Item> getItems(){
        return mutableItem;

    }
    public LiveData<List<CartItem>> getCart(){
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Item item){
        return cartRepo.addItemToCart(item);

    }

    public void removeItemfromCart(CartItem cartItem){
        cartRepo.removeItemFromCart(cartItem);

    }
    public void changeQuantity(CartItem cartItem, int quantity){
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice(){
        return cartRepo.getTotalPrice();
    }

    public void resetcart(){
        cartRepo.initCart();
    }
}
