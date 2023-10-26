package com.example.my_pondicherry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_info extends AppCompatActivity {

    TextView tuser,temail,tphone,tbio,tlast,tcomment;
    CircleImageView tprofile;
    Uri profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        profile = Uri.parse(intent.getStringExtra("profile"));
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String bio = intent.getStringExtra("bio");
        String first = intent.getStringExtra("first");
        String last = intent.getStringExtra("last");
        String comment = intent.getStringExtra("comment");
        String time = intent.getStringExtra("time");

        tuser = findViewById(R.id.user_name);
        tphone = findViewById(R.id.phone);
        temail = findViewById(R.id.email);
        tcomment = findViewById(R.id.comment);
        tprofile = findViewById(R.id.profile_pic);
        tlast = findViewById(R.id.lastname);
        tbio = findViewById(R.id.bio);

        tuser.setText(user);
        tphone.setText(phone);
        temail.setText(email);
        tcomment.setText(comment);
        tlast.setText(last);
        tbio.setText(bio);
        Glide.with(this).load(profile).into(tprofile);

    }
}