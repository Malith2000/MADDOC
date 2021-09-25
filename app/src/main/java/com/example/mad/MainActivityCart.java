package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mad.models.CartItem;
import com.example.mad.viewmodels.MenuViewModel;

import java.util.List;

public class MainActivityCart extends AppCompatActivity {

    private static final String TAG = "MainActivityCart";
    NavController navController;
    MenuViewModel menuViewModel;

    private int cartQuantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity =0;
                for(CartItem cartItem: cartItems){
                    quantity += cartItem.getQuantity();

                }
                cartQuantity=quantity;
                invalidateOptionsMenu();
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);
    }
    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.cartFragment2);
        View actionView = menuItem.getActionView();

        TextView cartBadgeTextView = findViewById(R.id.cart_badge_text_view);

        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        cartBadgeTextView.setVisibility(cartQuantity==0? View.GONE : View.VISIBLE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}