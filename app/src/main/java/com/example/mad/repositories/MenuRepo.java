package com.example.mad.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mad.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuRepo {
    private MutableLiveData<List<Item>> mutableItemList;
    public LiveData<List<Item>> getItem(){
        if (mutableItemList == null){
            mutableItemList= new MutableLiveData<>();
            loadItem();

        }
        return mutableItemList;
    }
    private void loadItem(){
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(UUID.randomUUID().toString(), "Seafood Fried Rice", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Beef Fried Rice", 500, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Pork Fried Rice", 500, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Egg Fried Rice", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Mongolian Fried Rice", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Mix Fried Rice", 400, true, ""));

        itemList.add(new Item(UUID.randomUUID().toString(), "Seafood Kottu", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Beef Kottu", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Pork Kottu", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Egg Kottu", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Dolphin Kottu", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Cheese Kottu", 400, true, ""));

        itemList.add(new Item(UUID.randomUUID().toString(), "Seafood Noodles", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Beef Noodles", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Pork Noodles", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Egg Noodles", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Cheese Noodles", 400, true, ""));
        itemList.add(new Item(UUID.randomUUID().toString(), "Ramen Noodles", 400, true, ""));


        mutableItemList.setValue(itemList);
    }
}
