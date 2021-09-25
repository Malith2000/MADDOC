package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountMenu extends AppCompatActivity {

    Button account,btnLogout,accountupdate,accountdelete;
    public ImageButton homeBtn,imagebutton2;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        accountdelete = (Button) findViewById(R.id.button11);
        accountdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                Intent intentam = new Intent(AccountMenu.this, UserAccount.class);
                startActivity(intentam);
            }
        });


        accountupdate = (Button) findViewById(R.id.button7);
        accountupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                Intent intentam = new Intent(AccountMenu.this, updateAcc.class);
                startActivity(intentam);
            }
        });

        homeBtn = (ImageButton)findViewById(R.id.imageButton23);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(AccountMenu.this,Menu.class);
                startActivity(intent3);
            }
        });

        imagebutton2 = (ImageButton)findViewById(R.id.imageButton22);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AccountMenu.this, UserAccount.class);
                startActivity(intent2);
            }
        });

        account = (Button) findViewById(R.id.button8);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                Intent intentam = new Intent(AccountMenu.this, UserAccount.class);
                startActivity(intentam);
            }
        });

        btnLogout = (Button)findViewById(R.id.button9);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AccountMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView textView17TextView = (TextView)findViewById(R.id.textView17);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    String textView31 = userprofile.Firstname;
                    textView17TextView.setText("Hi "+ textView31 + "!");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountMenu.this,"Something Wrong Happened",Toast.LENGTH_LONG).show();
            }

        });



    }
}