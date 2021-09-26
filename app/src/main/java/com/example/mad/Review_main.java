package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Review_main extends AppCompatActivity {

    Button customerReview,myReview,addReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);

        customerReview = (Button) findViewById(R.id.button15);
        customerReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                Intent intentam = new Intent(Review_main.this, Cus_review.class);
                startActivity(intentam);
            }
        });

        myReview = (Button) findViewById(R.id.button25);
        myReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                Intent intentam = new Intent(Review_main.this, MyReviews.class);
                startActivity(intentam);
            }
        });

        addReview = (Button) findViewById(R.id.button14);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v5) {

                Intent intentam = new Intent(Review_main.this, Feedback.class);
                startActivity(intentam);
            }
        });






    }
}