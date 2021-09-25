package com.example.mad.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mad.models.Item;
import com.example.mad.repositories.MenuRepo;

import java.util.List;

public class MenuViewModel extends ViewModel {

    MenuRepo menuRepo = new MenuRepo();

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
}
