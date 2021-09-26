package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mad.DatabaseDataTypes.Admin;
import com.example.mad.DatabaseDataTypes.CallBackInterface;
import com.example.mad.DatabaseDataTypes.FoodItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String LOG_TAG = AddFood.class.getSimpleName();
    private static final String[] categories = {"Rice", "Kottu", "Noodles", "Beverages"};
    public static final int FOOD_IMAGE_REQUEST_CODE=100;
    private Spinner spinner;
    private DatabaseReference reference;
    StorageReference storageRef;
    private String selectedCategory="";
    private EditText itemName;
    private EditText itemPrice;
    private ImageView itemImage;
    private Button submitBtn;

    private ImageButton quickmenu,imagebutton23;

    private Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        reference = FirebaseDatabase.getInstance().getReference("Categories");
        storageRef = FirebaseStorage.getInstance().getReference("Images");

        initHeaderIcons();
        initialiseSpinner();

        /* Hamburger menu*/
         quickmenu = (ImageButton)findViewById(R.id.imageButton24);
        quickmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(AddFood.this, AccountMenu.class);
                startActivity(intent3);
            }
        });

        itemImage=findViewById(R.id.foodImage);
        itemName = findViewById(R.id.addFoodName);
        itemPrice = findViewById(R.id.addFoodPrice);
        submitBtn = findViewById(R.id.button10);

        itemImage.setImageResource(R.drawable.download);
//        on click listener to select image
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemName.getText().toString();
                String price = itemPrice.getText().toString();
                if(selectedCategory.isEmpty() || name.isEmpty() || price.isEmpty()){
                    Log.d(LOG_TAG, "Validation error");
                    Toast.makeText(AddFood.this, "Please select a category",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(LOG_TAG, "Validation complete. calling database handler.");
                    databaseHandler(new FoodItem(name,price));
                }
            }
        });

    }

    private void databaseHandler(FoodItem item){
        addItemToDatabase(item, new CallBackInterface() {
            @Override
            public void onCallback(boolean value) {
                if (value) {
                    Log.d(LOG_TAG, "Added food item to database.");
                    Toast.makeText(AddFood.this, "Added item to database.",Toast.LENGTH_LONG).show();
                } else {
                    Log.d(LOG_TAG, "Not added to database. Please try again.");
                    Toast.makeText(AddFood.this, "Not added to database. Please try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void addItemToDatabase(FoodItem item, CallBackInterface callback){
        try {
            Log.d(LOG_TAG, "Adding item to db");
            String timestamp =String.valueOf(System.currentTimeMillis());
            reference.child(selectedCategory).child(timestamp).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    Log.d(LOG_TAG, "Adding item to db complete.");
                    uploadImageToStorage(timestamp);
                    callback.onCallback(true);
                }
            });
        }catch(Exception e){
            Log.d(LOG_TAG, "Unexpected Error occured while adding the item to database.");
            e.printStackTrace();
        }
    }

    private void uploadImageToStorage(String timestamp){
        // Get the data from an ImageView as bytes

        Bitmap bitmap = ((BitmapDrawable) itemImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.child(selectedCategory).child(timestamp).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(LOG_TAG, "Image upload failed.");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(LOG_TAG, "Image upload complete."+storageRef.child(selectedCategory).child(timestamp).getPath());
            }
        });
    }

    /* opening gallery and image retrieving code below*/
    private void openGallery() {
            Log.d(LOG_TAG, "Open gallery method called.");
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, FOOD_IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
        if (resultCode == RESULT_OK && requestCode == FOOD_IMAGE_REQUEST_CODE){
            Log.d(LOG_TAG,"Photo received!");
            image = data.getData();
            itemImage.setImageURI(image);
        }else{
            itemImage.setImageResource(R.drawable.download);
        }
        }catch(Exception e){
            itemImage.setImageResource(R.drawable.download);
            e.printStackTrace();
        }
    }

    /* Dropdown related methods are below.*/
    public void initialiseSpinner(){
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setSelectedCategory(categories[position]);
        Toast.makeText(this,"Selected "+getSelectedCategory(),Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this,"Category not selected !",Toast.LENGTH_SHORT);
    }

    private void initHeaderIcons(){
        /* Hamburger menu*/
        quickmenu = (ImageButton)findViewById(R.id.imageButton24);
        quickmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(AddFood.this, AccountMenu.class);
                startActivity(intent3);
            }
        });

        imagebutton23 = (ImageButton)findViewById(R.id.imageButton23);
        imagebutton23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AddFood.this, AdminMenuView.class);
                startActivity(intent2);
            }
        });




    }
}