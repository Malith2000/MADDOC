package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class updateAcc extends AppCompatActivity {
    private EditText  upFn, upLn, upPno, upEm, upPw;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button deleteAccount;
    private FirebaseDatabase database;
    private User account;
    private static String LOG_TAG= updateAcc.class.getSimpleName();
    String PersonName6,PersonName7,Phone3,EmailAddress3,Password5;
    public ImageButton quickmenu,homeBtn,imagebutton2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_acc);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        imagebutton2 = (ImageButton)findViewById(R.id.imageButton22);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(updateAcc.this, useraccount.class);
                startActivity(intent2);
            }
        });

        quickmenu = (ImageButton)findViewById(R.id.imageButton24);
        quickmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(updateAcc.this, AccountMenu.class);
                startActivity(intent3);
            }
        });

        homeBtn = (ImageButton)findViewById(R.id.imageButton23);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(updateAcc.this,Menu.class);
                startActivity(intent3);
            }
        });


         upFn =  findViewById(R.id.editTextTextPersonName6);
         upLn =  findViewById(R.id.editTextTextPersonName7);
         upPno =  findViewById(R.id.editTextPhone3);
         upEm =  findViewById(R.id.editTextTextEmailAddress3);
         upPw=  findViewById(R.id.editTextTextPassword5);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofileup = snapshot.getValue(User.class);

                if (userprofileup != null) {
                     PersonName6 = userprofileup.Firstname;
                     PersonName7 = userprofileup.Lastname;
                     EmailAddress3 = userprofileup.Email;
                     Phone3 = userprofileup.PhoneNo;
                     Password5 = userprofileup.Password;

                    upFn.setText(PersonName6);
                    upLn.setText(PersonName7);
                    upPno.setText(Phone3);
                    upEm.setText(EmailAddress3);
                    upPw.setText(Password5);


                }

                //updateBtn.setOnClickListener(new View.OnClickListener() {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateAcc.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });






         /*updateBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 updateProfile();
             }
         });*/

         /*user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final EditText editTextTextPersonName6EditText = (EditText) findViewById(R.id.editTextTextPersonName6);
        final EditText editTextTextPersonName7EditText = (EditText) findViewById(R.id.editTextTextPersonName7);
        final EditText editTextPhone3EditText = (EditText) findViewById(R.id.editTextPhone3);
        final EditText editTextTextEmailAddress3EditText = (EditText) findViewById(R.id.editTextTextEmailAddress3);
        final EditText editTextTextPassword5EditText = (EditText) findViewById(R.id.editTextTextPassword5);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofileup = snapshot.getValue(User.class);

                if (userprofileup != null) {
                    String editTextTextPersonName6 = userprofileup.Firstname;
                    String editTextTextPersonName7 = userprofileup.Lastname;
                    String editTextTextEmailAddress3 = userprofileup.Email;
                    String editTextPhone3 = userprofileup.PhoneNo;
                    String editTextTextPassword5 = userprofileup.Password;

                    editTextTextPersonName6EditText.setText(editTextTextPersonName6);
                    editTextTextPersonName7EditText.setText(editTextTextPersonName7);
                    editTextPhone3EditText.setText(editTextPhone3);
                    editTextTextEmailAddress3EditText.setText(editTextTextEmailAddress3);
                    editTextTextPassword5EditText.setText(editTextTextPassword5);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateAcc.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });*/




    }
    public void update(View view){
        try {
            if (isfNamechanged()|islNamechanged()|isPnoChanged()|isEmailChanged()|isPwChanged()) {
                Toast.makeText(this, "updated", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.d(LOG_TAG,"update failed");
           e.printStackTrace();

        }
    }



    private boolean isPwChanged() {
        final String newPwd=upPw.getText().toString();
        if(!Password5.equals(newPwd)){
            reference.child(userID).child("Password").setValue(upPw.getText().toString());
            Log.d(LOG_TAG,"pasword updated ") ;
            user.updatePassword(newPwd).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG,"firebase password updated ") ;
                    }
                }
            });
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        final String newEmail=upEm.getText().toString();
        if(!EmailAddress3.equals(newEmail)){
            reference.child(userID).child("Email").setValue(newEmail);
            Log.d(LOG_TAG,user.getEmail().toString());
            user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG,"firebase email address updated ") ;
                    }
                }
            });
            return true;
        }else{
            return false;
        }
    }

    private boolean isPnoChanged() {
        if(!Phone3.equals(upPno.getText().toString())){
            reference.child(userID).child("PhoneNo").setValue(upPno.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean islNamechanged() {
        if(!PersonName7.equals(upLn.getText().toString())){
            reference.child(userID).child("Lastname").setValue(upLn.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isfNamechanged() {

        Log.d(LOG_TAG,"entered name changed ") ;
        Log.d(LOG_TAG,"person name:  "+PersonName6) ;
        Log.d(LOG_TAG,"upFn text:  "+upFn.getText().toString()) ;

        if(!PersonName6.equals(upFn.getText().toString())){
            Log.d(LOG_TAG,"entered if ") ;
            Log.d(LOG_TAG,"userID:  "+userID) ;

            reference.child(userID).child("Firstname").setValue(upFn.getText().toString());
            Log.d(LOG_TAG,"eupdate complete") ;

            return true;
        }else{
            Log.d(LOG_TAG,"entered else") ;

            return false;
        }

    }

}

