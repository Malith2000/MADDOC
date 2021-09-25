package com.example.mad.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mad.R;
import com.example.mad.databinding.FragmentItemDetailBinding;
import com.example.mad.viewmodels.MenuViewModel;

import org.jetbrains.annotations.NotNull;


public class ItemDetailFragment extends Fragment {

    FragmentItemDetailBinding fragmentItemDetailBinding;
    MenuViewModel menuViewModel;



    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentItemDetailBinding = FragmentItemDetailBinding.inflate(inflater, container, false);
        return fragmentItemDetailBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
        fragmentItemDetailBinding.setMenuViewModel(menuViewModel);
    }
}