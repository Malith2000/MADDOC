package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyReviews extends AppCompatActivity {

    RecyclerView recyclerView;
    MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        recyclerView = (RecyclerView)findViewById(R.id.my_rev_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Reviews> options =
                new FirebaseRecyclerOptions.Builder<Reviews>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reviews"), Reviews.class)
                        .build();

        myListAdapter = new MyListAdapter(options);
        recyclerView.setAdapter(myListAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myListAdapter.stopListening();
    }

}