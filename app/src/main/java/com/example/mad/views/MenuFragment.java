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
import com.example.mad.adapters.MenuListAdapter;
import com.example.mad.databinding.FragmentMenuBinding;
import com.example.mad.models.Item;
import com.example.mad.viewmodels.MenuViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MenuFragment extends Fragment implements MenuListAdapter.MenuInterface {

    private static final String TAG = "MenuFragment";

    FragmentMenuBinding fragmentMenuBinding;
    private MenuListAdapter menuListAdapter;
    private MenuViewModel menuViewModel;
    private NavController navController;




    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater, container, false);
        return fragmentMenuBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuListAdapter = new MenuListAdapter(this);
        fragmentMenuBinding.menuRecyclerView.setAdapter(menuListAdapter);
        fragmentMenuBinding.menuRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentMenuBinding.menuRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
        menuViewModel.getItem().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                menuListAdapter.submitList(items);

            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void onItemClick(Item item) {
        Log.d(TAG, "onItemClick: " + item.toString());
        menuViewModel.setItem(item);
        navController.navigate(R.id.action_menuFragment3_to_itemDetailFragment3);

    }
}