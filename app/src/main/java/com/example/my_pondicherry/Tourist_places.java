package com.example.my_pondicherry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tourist_places extends AppCompatActivity {



    public static  final  String KEY = "place";
    CardView c1,c2,c3,c4,c5,c6,c7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_places);


        c1 = findViewById(R.id.p1);
        c2 = findViewById(R.id.p2);
        c3 = findViewById(R.id.p3);
        c4 = findViewById(R.id.p4);
        c5 = findViewById(R.id.p5);
        c6 = findViewById(R.id.p6);
        c7 = findViewById(R.id.p7);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"rockbeach");
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"para");
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"scooba");
                startActivity(intent);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"paradise");
                startActivity(intent);
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"aroville");
                startActivity(intent);
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"botanical");
                startActivity(intent);
            }
        });

        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tourist_places.this, Place_details.class);
                intent.putExtra(KEY,"mangroo");
                startActivity(intent);
            }
        });
    }
}