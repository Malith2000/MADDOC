package com.example.mad.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mad.R;
import com.example.mad.adapters.CartListAdapter;
import com.example.mad.databinding.FragmentCartBinding;
import com.example.mad.models.CartItem;
import com.example.mad.viewmodels.MenuViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    private static final String TAG = "CartFragment";
    MenuViewModel menuViewModel;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
     return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        final CartListAdapter cartListAdapter = new CartListAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
        menuViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartListAdapter.submitList(cartItems);
                fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size()>0);

            }
        });

        menuViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                fragmentCartBinding.orderTotalTextView.setText("Total: Rs " + aDouble.toString());
            }
        });

        fragmentCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_cartFragment2_to_orderFragment2);
            }
        });


    }

    @Override
    public void deleteItem(CartItem cartItem) {
        menuViewModel.removeItemfromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        menuViewModel.changeQuantity(cartItem, quantity);
    }
}