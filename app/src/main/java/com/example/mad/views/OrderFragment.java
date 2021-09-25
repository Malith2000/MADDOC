package com.example.mad.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mad.R;
import com.example.mad.databinding.FragmentOrderBinding;
import com.example.mad.viewmodels.MenuViewModel;

import org.jetbrains.annotations.NotNull;


public class OrderFragment extends Fragment {

    NavController navController;
    FragmentOrderBinding fragmentOrderBinding;
    MenuViewModel menuViewModel;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_order, container, false);
        FragmentOrderBinding fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        return fragmentOrderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);

        fragmentOrderBinding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuViewModel.resetcart();
                navController.navigate(R.id.action_orderFragment2_to_menuFragment3);
            }
        });
    }
}