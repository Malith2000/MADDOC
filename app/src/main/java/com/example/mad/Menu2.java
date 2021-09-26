package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mad.DatabaseDataTypes.CategoryCallBack;
import com.example.mad.DatabaseDataTypes.FoodItem;
import com.example.mad.DatabaseDataTypes.Product;
import com.example.mad.DatabaseDataTypes.ProductCallBackInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class Menu2 extends AppCompatActivity {
    public static final String LOG_TAG = Menu2.class.getSimpleName();
    private DatabaseReference reference;
    StorageReference storageRef;
    private String category;
    private ArrayList<Product> foodItems = new ArrayList<>();
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        Intent intent = getIntent();

        category = intent.getStringExtra(Menu.CATEGORY_KEY);
        Log.d(LOG_TAG, "Selected category: " + category);

        reference = FirebaseDatabase.getInstance().getReference("Categories").child(category);
        storageRef = FirebaseStorage.getInstance().getReference("Images").child(category);
        layout = findViewById(R.id.productsLinearLayout);

        TextView t=(TextView)findViewById(R.id.fstTxt);
        t.setText(category);


        databaseHandler();


    }


    private void databaseHandler() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                getFromDB(new CategoryCallBack() {
                    @Override
                    public void onCallback(ArrayList<Product> products) {
                        if (products != null) {
                            Log.d(LOG_TAG, "Retrieved products from database.");
                            StringBuilder s = new StringBuilder("");
                            for (Product p : products) {
                                s.append(p.toString());
                                s.append("\n");
                                if (!foodItems.contains(p)) {
                                    foodItems.add(p);
                                }
                            }
                            Log.d(LOG_TAG, "list: " + foodItems);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(LOG_TAG, "inside run on ui: " + foodItems.size());
                                    if (foodItems.size() == 0) {
                                        TextView tv2 = new TextView(Menu2.this);
                                        tv2.setText("No products available at the moment.");
                                        tv2.setTextSize(20);
                                        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                                        tv2.setTextColor(getResources().getColor(R.color.black));

                                        layout.addView(tv2);
                                        return;
                                    }
                                    for (int i = 0; i < foodItems.size(); i++) {
                                        Product item = foodItems.get(i);
                                        // Reference to an image file in Cloud Storage
                                        StorageReference ref = storageRef.child(item.getId());
                                        ImageView imageView = new ImageView(Menu2.this);
                                        imageView.setImageResource(R.drawable.download);
                                        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(400, 400);
                                        lParams.setMargins(0, 20, 0, 20);
                                        imageView.setLayoutParams(lParams
                                        );


                                        LinearLayout layout1 = new LinearLayout(Menu2.this);
                                        layout1.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
                                        layout1.setGravity(Gravity.CENTER_HORIZONTAL);
                                        layout1.setOrientation(LinearLayout.VERTICAL);

                                        layout1.addView(imageView);


                                        TextView tv = new TextView(Menu2.this);
                                        tv.setText(item.getName());
                                        tv.setTextSize(20);
                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                        tv.setTextColor(getResources().getColor(R.color.black));
                                        layout1.addView(tv);

                                        TextView tv2 = new TextView(Menu2.this);
                                        tv2.setText(item.getPrice());
                                        tv2.setTextSize(20);
                                        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                                        tv2.setTextColor(getResources().getColor(R.color.black));
                                        layout1.addView(tv2);


                                        layout.addView(layout1);


                                        Glide.with(Menu2.this)
                                                .load(ref)
                                                .into(imageView);

                                    }


                                }
                            });
                        } else {
                            Log.d(LOG_TAG, "Unable to retrieve products.");
                            Toast.makeText(Menu2.this, "Unable to fetch products from database. Please reload the app and try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }


        }).start();


    }


    private void getFromDB(CategoryCallBack callback) {
        try {
            Log.d(LOG_TAG, "Adding item to db");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    ArrayList<Product> products1 = new ArrayList<>();
                    Log.d(LOG_TAG, "Retrieveing products for Category/" + category);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                    /* snap format is as follows
                    {
                      key = tyBpgK1ViOP0s5Ex1pN0xoA6E3b2,
                      value = {
                          Name="Fried Rice",
                          Price="450.00"
                        }
                    }
                     */

                        String itemId = snap.getKey();
                        FoodItem item = snap.getValue(FoodItem.class);

                        Product product = new Product(item.getName(), item.getPrice(), itemId);

//                            getImageForProduct(product, new ProductCallBackInterface() {
//                                @Override
//                                public void onCallback(Product product) {
//                                    products1.add(product);
//                                }
//                            });
                        products1.add(product);

                    }
                    callback.onCallback(products1);

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.d(LOG_TAG, "Database error. Couldn't fetch data.");
                }
            });


        } catch (Exception e) {
            Log.d(LOG_TAG, "Unexpected Error occured while retrieving data");
            e.printStackTrace();
        }
    }


}