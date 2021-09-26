package com.example.mad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UserAccount extends AppCompatActivity {
    private static final String LOG_TAG= UserAccount.class.getSimpleName();
    public Button buttonup;
    public ImageButton quickmenu,homeBtn;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Button deleteAccount;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccount);

        quickmenu = (ImageButton)findViewById(R.id.imageButton24);
        quickmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(UserAccount.this, AccountMenu.class);
                startActivity(intent3);
            }
        });

        homeBtn = (ImageButton)findViewById(R.id.imageButton23);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                Intent intent3 = new Intent(UserAccount.this,Menu.class);
                startActivity(intent3);
            }
        });


        buttonup = (Button)findViewById(R.id.button4);
        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent intent1 = new Intent(UserAccount.this, updateAcc.class);
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
                Toast.makeText(UserAccount.this,"Something Wrong Happened",Toast.LENGTH_LONG).show();
            }
        });

        deleteAccount = findViewById(R.id.button5);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserAccount.this);
                dialog.setTitle("Are you Sure?");
                dialog.setMessage("Deleting this account will completely remove your account from the system");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    deleteUserFromDB();
                                    Toast.makeText(UserAccount.this,"Account Deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UserAccount.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(UserAccount.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();

            }
        });




    }


    private void deleteUserFromDB(){
        reference.child(userID).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                Log.d(LOG_TAG, "User deletion successful.");
            }
        }) ;


    }


}