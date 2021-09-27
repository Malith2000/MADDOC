package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.mad.DatabaseDataTypes.CartItem;
import com.example.mad.DatabaseDataTypes.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CartDisplay extends AppCompatActivity {
    private static final String LOG_TAG=CartDisplay.class.getSimpleName();
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    LinearLayout layout;
    StorageReference storageRef;
    private double totalPrice;
    private TextView totalPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);
        layout=findViewById(R.id.cart_layout);
        totalPriceText=findViewById(R.id.totalPriceText);
        storageRef = FirebaseStorage.getInstance().getReference("Images");

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
                readFromFile();
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
        if(cartItems.size()==0){
            Log.d(LOG_TAG,"Empty cart");
            TextView tv = new TextView(CartDisplay.this);
            tv.setText("Cart empty !");
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(getResources().getColor(R.color.black));

            layout.addView(tv);
            return;
        }
        Log.d(LOG_TAG,"cart: "+cartItems.toString());


        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if(item.getQuantity()<1){
                continue;
            }
            // Reference to an image file in Cloud Storage
            StorageReference ref = storageRef.child(item.getCategory()).child(item.getImageId());

            ImageView imageView = new ImageView(CartDisplay.this);
            imageView.setImageResource(R.drawable.download);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(150, 150);
            lParams.setMargins(0, 20, 0, 20);
            imageView.setLayoutParams(lParams
            );


            LinearLayout layout1 = new LinearLayout(CartDisplay.this);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(600, 1200));
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
            tv3.setText(String.valueOf(item.getQuantity()));
            tv3.setTextSize(20);
            tv3.setGravity(Gravity.CENTER_HORIZONTAL);
            tv3.setTextColor(getResources().getColor(R.color.black));

            Button removeItem = new Button(this);
            removeItem.setText("-");
            removeItem.setTextSize(30);
            removeItem.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (item.getQuantity()>0) {
                        setTotalPrice(getTotalPrice() - Double.parseDouble(item.getPrice().replaceAll("\\D+","")));
                        item.setQuantity(item.getQuantity()-1);
                        if(item.getQuantity()==0){
                            cartItems.remove(item);
                        }
                        tv3.setText(String.valueOf(item.getQuantity()));
                        calculateTotal();

                    } else {
                        Toast.makeText(CartDisplay.this, "Quantity of "+item.getCategory()+" is zero.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            layout1.addView(removeItem);


            layout1.addView(tv3);

            Button addItem = new Button(this);
            addItem.setText("+");
            addItem.setTextSize(30);
            addItem.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTotalPrice(getTotalPrice() + Double.parseDouble(item.getPrice().replaceAll("\\D+","")));
                    Log.d(LOG_TAG,"price: "+item.getPrice().replaceAll("\\D+",""));
                    if(cartItems.contains(item)){
                        item.setQuantity(item.getQuantity()+1);
                    }else{
                        item.setQuantity(1);
                        cartItems.add(item);

                    }
                    tv3.setText(String.valueOf(item.getQuantity()));
                    calculateTotal();
                }
            });
            layout1.addView(addItem);

            layout.addView(layout1);
            Glide.with(CartDisplay.this)
                    .load(ref)
                    .into(imageView);

        }
        //
    }

    private void readFromFile() {
        Log.d(LOG_TAG,"reading from file");
        FileInputStream is =null;
        ObjectInputStream ois=null;
        try {
            File file = new File(this.getFilesDir()  +"cart1.txt");
            is = new FileInputStream(file);
            ois = new ObjectInputStream(is);

            while (is.available()!=0) {
                CartItem cartItem = (CartItem) ois.readObject();
                Log.d(LOG_TAG, "Has cart object: "+cartItem.toString());
                if(cartItems.contains(cartItem)){
                    for(CartItem i:cartItems){
                        if(i.equals(cartItem)){
                            i.setQuantity(i.getQuantity()+1);
                        }
                    }
                }else{
                    cartItems.add(cartItem);
                }
            }
            Log.d(LOG_TAG,"reading from file complete");
            Log.d(LOG_TAG, "Cart items in read: "+cartItems.toString());
            calculateTotal();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (is != null) {
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"writing to  file ");
        FileOutputStream fos =null;
        ObjectOutputStream oos=null;

        try{
            File file = new File(this.getFilesDir()  +"cart1.txt");
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            for(CartItem i: cartItems){
                oos.writeObject(i);
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }


    public void calculateTotal (){
        int total=0;
        for(CartItem i: cartItems){
            total+=i.getQuantity() *Double.parseDouble(i.getPrice().replaceAll("\\D+",""));
        }
        totalPrice=total;
        totalPriceText.setText(String.valueOf(totalPrice));
    }
}