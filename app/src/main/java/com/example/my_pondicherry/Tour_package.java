package com.example.my_pondicherry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tour_package extends AppCompatActivity {


    Button gpay1;
    public static  final  String KEY = "place";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_package);

        gpay1 = findViewById(R.id.gpay1);

        gpay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tour_package.this, Payment.class);
                intent.putExtra(KEY,"rockbeach");
                startActivity(intent);
            }
        });

    }
}