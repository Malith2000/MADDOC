package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {
    public static final String CATEGORY_KEY=Menu.class.getSimpleName()+"_CATEGORY";
    public Button button1;
    public ImageButton imagebutton2,quickmenu;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initHeaderIcons();


        findViewById(R.id.btnRice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(Menu.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Rice");
                startActivity(intent1);
            }
        });

        findViewById(R.id.btnNoodles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(Menu.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Noodles");
                startActivity(intent1);
            }
        });

        findViewById(R.id.btnKottu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(Menu.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Kottu");
                startActivity(intent1);
            }
        });

        findViewById(R.id.btnBeverages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(Menu.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Beverages");
                startActivity(intent1);
            }
        });

    }

    private void initHeaderIcons(){
        /* Hamburger menu*/
        quickmenu = (ImageButton)findViewById(R.id.imageButton24);
        quickmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(Menu.this, AccountMenu.class);
                startActivity(intent3);
            }
        });

        // init cart icon
        findViewById(R.id.imageButton21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Menu.this, CartDisplay.class);
                startActivity(intent1);
            }
        });



        /*User account icon*/
        imagebutton2 = (ImageButton)findViewById(R.id.imageButton22);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Menu.this, UserAccount.class);
                startActivity(intent2);
            }
        });


    }




}