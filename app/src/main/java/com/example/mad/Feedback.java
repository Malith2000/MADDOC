package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Feedback extends AppCompatActivity {

    EditText name, email, date, message;
    Button submitButton;

    DatabaseReference reviewDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        name = findViewById(R.id.fname);
        email = findViewById(R.id.email);
        date= findViewById(R.id.date);
        message= findViewById(R.id.message);
        submitButton = findViewById(R.id.submit_buttn);

        reviewDbRef = FirebaseDatabase.getInstance().getReference().child("Reviews");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String e_mail = email.getText().toString();
                String date1 = date.getText().toString();
                String messa = message.getText().toString();

                Reviews reviews = new Reviews(name1,e_mail,date1,messa);

                if(name1.isEmpty() && e_mail.isEmpty() && date1.isEmpty() && messa.isEmpty()){
                    Toast.makeText(Feedback.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }

                else if(name1.isEmpty()){
                    name.setError("Please Enter Name");
                    name.requestFocus();
                }


                else if(e_mail.isEmpty()){
                    email.setError("Please Enter email address");
                    email.requestFocus();
                }
                else if(date1.isEmpty()){
                    date.setError("Enter date");
                    date.requestFocus();
                }
                else if(messa.isEmpty()){
                    message.setError("Please Enter the message");
                    message.requestFocus();
                }

                else {
                    reviewDbRef.push().setValue(reviews);
                    Toast.makeText(Feedback.this, "Data inserted!", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

}