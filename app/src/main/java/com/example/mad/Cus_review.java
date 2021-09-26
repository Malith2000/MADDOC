package com.example.mad;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Cus_review extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter listAdapter;

    DatabaseReference reviewDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_review);

        recyclerView = (RecyclerView)findViewById(R.id.rev_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Reviews> options =
                new FirebaseRecyclerOptions.Builder<Reviews>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Reviews"), Reviews.class)
                .build();

        listAdapter = new ListAdapter(options);
        recyclerView.setAdapter(listAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listAdapter.stopListening();
    }
}