package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad.DatabaseDataTypes.Admin;
import com.example.mad.DatabaseDataTypes.CallBackInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG= MainActivity.class.getSimpleName();
    EditText email,password;
    FirebaseAuth mFirebaseAuth;
    public Button button;
    public Button button1;

    private FirebaseAuth.AuthStateListener mAuthstateListner;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Admins");

        email = findViewById(R.id.editTextTextEmailAddress5);
        password = findViewById(R.id.editTextTextPassword);
        button1 = findViewById(R.id.button);

        mAuthstateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {

                    Toast.makeText(MainActivity.this, "You Are Logged In", Toast.LENGTH_SHORT).show();

                    redirectToHome(mFirebaseUser.getEmail());
                } else {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                String Email = email.getText().toString();
                String pwd = password.getText().toString();

                if (Email.isEmpty()) {
                    email.setError("Please Enter UserName");
                    email.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                } else if (Email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(Email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(Email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            } else {
                                redirectToHome(Email);
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void redirectToHome(String email){
        isAnAdminUser(email, new CallBackInterface() {
            @Override
            public void onCallback(boolean value) {
                if (value) {
                    Log.d(LOG_TAG, "Is an admin user.");
                    /*TODO should redirect to another screen*/
                    Intent intentmenu = new Intent(MainActivity.this, AccountMenu.class);
                    startActivity(intentmenu);
                } else {
                    Log.d(LOG_TAG, "Not an admin user.");
                    Intent intentmenu = new Intent(MainActivity.this, Menu.class);
                    startActivity(intentmenu);
                }
            }
        });
    }

    /**
     *
     * @param email - email should contain the logged in email of the user.
     * @param callback- a callback object. It should implement the CallBackInterface.
     *               This will be used to return the value, once received.
     */
    private void isAnAdminUser(String email, CallBackInterface callback){
        try {
        Log.d(LOG_TAG, "Checking whether the user is an admin.");
        ArrayList<String> emails = new ArrayList<>();
        databaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    /* snap format is as follows
                    {
                      key = tyBpgK1ViOP0s5Ex1pN0xoA6E3b2,
                      value = {
                          Email=admin@gmail.com,
                          Password=admin123
                        }
                    }
                     */
                    try {
                        Admin a=snap.getValue(Admin.class);
                        emails.add(a.getEmail());
                    }catch(Exception e){
                        Log.d(LOG_TAG, "IUnexpected Error occured while checking the user type.");
                        e.printStackTrace();
                    }
                }
                callback.onCallback(emails.contains(email));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        }catch(Exception e){
            Log.d(LOG_TAG, "IUnexpected Error occured while checking the user type.");
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthstateListner);
    }
}
