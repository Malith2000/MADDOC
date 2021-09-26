package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class AdminMenuView extends AppCompatActivity {

    public static final String CATEGORY_KEY=Menu.class.getSimpleName()+"_CATEGORY";
    public Button button1,button12;
    public ImageButton imagebutton2,quickmenu,imagebutton23;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_view);

        initHeaderIcons();


        findViewById(R.id.btnRice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(AdminMenuView.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Rice");
                startActivity(intent1);
            }
        });

        findViewById(R.id.btnNoodles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(AdminMenuView.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Noodles");
                startActivity(intent1);
            }
        });

        findViewById(R.id.btnKottu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(AdminMenuView.this, Menu2.class);
                intent1.putExtra(CATEGORY_KEY, "Kottu");
                startActivity(intent1);
            }
        });

        findViewById(R.id.btnBeverages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(AdminMenuView.this, Menu2.class);
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

                Intent intent3 = new Intent(AdminMenuView.this, AccountMenu.class);
                startActivity(intent3);
            }
        });


        /*User account icon*/
        imagebutton2 = (ImageButton)findViewById(R.id.imageButton22);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminMenuView.this, UserAccount.class);
                startActivity(intent2);
            }
        });

        button12 = (Button)findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminMenuView.this, AddFood.class);
                startActivity(intent2);
            }
        });




    }
}