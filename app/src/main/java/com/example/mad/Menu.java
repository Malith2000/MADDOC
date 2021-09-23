package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {
    public Button button1;
    public ImageButton imagebutton2,quickmenu;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        quickmenu = (ImageButton)findViewById(R.id.imageButton24);
        quickmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(Menu.this, AccountMenu.class);
                startActivity(intent3);
            }
        });



        button1 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(Menu.this, Menu2.class);
                startActivity(intent1);
            }
        });
        imagebutton2 = (ImageButton)findViewById(R.id.imageButton22);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Menu.this, useraccount.class);
                startActivity(intent2);
            }
        });
    }
}