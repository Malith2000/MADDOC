package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mad.DatabaseDataTypes.CartItem;
import com.example.mad.DatabaseDataTypes.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CartDisplay extends AppCompatActivity {
    private ArrayList<CartItem> cart = new ArrayList<>();
    LinearLayout layout;
    StorageReference storageRef;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);
        layout=findViewById(R.id.cart_layout);
        storageRef = FirebaseStorage.getInstance().getReference("Images").child(category);

        totalPrice=0;
        cartInit();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void cartInit(){
        new Thread(new Runnable() {
            @Override
            public void run() {




//                update  UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCart();
                    }
                });
            }
        }).start();
    }


    private void updateCart(){
        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            // Reference to an image file in Cloud Storage
            StorageReference ref = storageRef.child(item.getImageId());
            ImageView imageView = new ImageView(CartDisplay.this);
            imageView.setImageResource(R.drawable.download);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(400, 400);
            lParams.setMargins(0, 20, 0, 20);
            imageView.setLayoutParams(lParams
            );


            LinearLayout layout1 = new LinearLayout(CartDisplay.this);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
            layout1.setGravity(Gravity.CENTER_HORIZONTAL);
            layout1.setOrientation(LinearLayout.VERTICAL);

            layout1.addView(imageView);


            TextView tv = new TextView(CartDisplay.this);
            tv.setText(item.getName());
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(getResources().getColor(R.color.black));
            layout1.addView(tv);
            
            TextView tv2 = new TextView(CartDisplay.this);
            tv2.setText(item.getPrice());
            tv2.setTextSize(20);
            tv2.setGravity(Gravity.CENTER_HORIZONTAL);
            tv2.setTextColor(getResources().getColor(R.color.black));
            layout1.addView(tv2);


            TextView tv3 = new TextView(CartDisplay.this);
            tv3.setText(item.getPrice());
            tv3.setTextSize(20);
            tv3.setGravity(Gravity.CENTER_HORIZONTAL);
            tv3.setTextColor(getResources().getColor(R.color.black));
            layout1.addView(tv3);


            layout.addView(layout1);


            

        }
        //
    }
}