package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText email,password,fname,lname,phone;
    Button btnRegister;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fname = findViewById(R.id.editTextTextPersonName3);
        lname = findViewById(R.id.editTextTextPersonName9);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        btnRegister = findViewById(R.id.button3);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pwd = password.getText().toString();
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String pno = phone.getText().toString();
                
                if(Email.isEmpty()){
                    email.setError("Please Enter UserName");
                    email.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();
                }
                else if(firstname.isEmpty()){
                   fname.setError("Please Enter First Name");
                   fname.requestFocus();
                }
                else if(lastname.isEmpty()){
                    lname.setError("Please Enter Last Name");
                    lname.requestFocus();
                }
                else if(pno.isEmpty()){
                    phone.setError("Please Enter Phone.No");
                    phone.requestFocus();
                }
                else if(Email.isEmpty() && pwd.isEmpty() && firstname.isEmpty() && lastname.isEmpty() && pno.isEmpty()){
                    Toast.makeText(Registration.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(Email.isEmpty() && pwd.isEmpty() && firstname.isEmpty() && lastname.isEmpty() && pno.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(Email,pwd).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Registration.this,"Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(Registration.this,MainActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Registration.this,"Error Occured!",Toast.LENGTH_SHORT).show();

                }

            }
            
        });



    }
}