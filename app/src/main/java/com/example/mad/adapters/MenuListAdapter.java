package com.example.mad.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.databinding.MenuRowBinding;
import com.example.mad.models.Item;

import org.jetbrains.annotations.NotNull;

public class MenuListAdapter extends ListAdapter<Item, MenuListAdapter.menuViewHolder> {

    MenuInterface menuInterface;
    public MenuListAdapter(MenuInterface menuInterface) {

        super(Item.itemCallback);
        this.menuInterface = menuInterface;
    }

    @NonNull
    @NotNull
    @Override
    public menuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MenuRowBinding menuRowBinding = MenuRowBinding.inflate(layoutInflater, parent, false);
        menuRowBinding.setMenuInterface(menuInterface);

        return new menuViewHolder(menuRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MenuListAdapter.menuViewHolder holder, int position) {
        Item item = getItem(position);
        holder.menuRowBinding.setItem(item);

    }

    class menuViewHolder extends RecyclerView.ViewHolder{

        MenuRowBinding menuRowBinding;
        public menuViewHolder(MenuRowBinding binding) {
            super(binding.getRoot());
            this.menuRowBinding = binding;


        }
    }
    public interface MenuInterface{
        void addItem(Item item);
        void onItemClick(Item item);
    }
    }

