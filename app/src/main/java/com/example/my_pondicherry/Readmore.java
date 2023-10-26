package com.example.my_pondicherry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Readmore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readmore);

        Intent intent = getIntent();
        String place = intent.getStringExtra(Tourist_places.KEY);


    }
}