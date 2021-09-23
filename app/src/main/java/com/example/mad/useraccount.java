package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class useraccount extends AppCompatActivity {
    public Button buttonup;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccount);

        buttonup = (Button)findViewById(R.id.button4);
        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(useraccount.this, updateAcc.class);
                startActivity(intent1);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView textView31TextView = (TextView)findViewById(R.id.textView31);
        final TextView textView32TextView = (TextView)findViewById(R.id.textView32);
        final TextView textView33TextView = (TextView)findViewById(R.id.textView33);
        final TextView textView39TextView = (TextView)findViewById(R.id.textView39);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    String textView31 = userprofile.Firstname;
                    String textView32 = userprofile.Lastname;
                    String textView33 = userprofile.Email;
                    String textView39 = userprofile.PhoneNo;

                    textView31TextView.setText(textView31);
                    textView32TextView.setText(textView32);
                    textView33TextView.setText(textView33);
                    textView39TextView.setText(textView39);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(useraccount.this,"Something Wrong Happened",Toast.LENGTH_LONG).show();
            }
        });




    }


}