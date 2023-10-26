package com.example.my_pondicherry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Animation topAnim,bottomAnim,left,right;
    BottomNavigationView bottomNav;
    Button btn1,btn2;
    final String[] it = new String[1];
    int con=0;
    private long pressedTime;
    SharedPreferences sharedPreferences;
    TextView t,b;
    ImageView tplace,tpackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottomNav);
        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        tplace = findViewById(R.id.touristplace);
        tpackage = findViewById(R.id.toursit_package);
        t = findViewById(R.id.t);
        b = findViewById(R.id.b);
        topAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_animation);
        left =AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_to_right);
        right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left);

        tplace.setAnimation(right);
        tpackage.setAnimation(left);

        t.setAnimation(bottomAnim);
        b.setAnimation(topAnim);

        tplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, Tourist_places.class);
                startActivity(in);
            }
        });

        tpackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, Tour_package.class);
                startActivity(in);
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(item -> {

            it[0] =item.getTitle().toString();

            if(it[0].equals("Home")) {

            }
            else if (it[0].equals("Upload"))
            {
                Intent in = new Intent(this, About.class);
                startActivity(in);
            }
            else if(it[0].equals("Account"))
            {
                Intent in = new Intent(this, User_Profile.class);
                startActivity(in);
            }
            else if(it[0].equals("Guide"))
            {
                Intent in = new Intent(this, Tour_guide.class);
                startActivity(in);
            }
            return true;
        });

    }

    public void frag_imp(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag , fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

//                if(s.charAt(0)=='H' || s.charAt(0)=='h')
//                    con=1;
//                else if(s.charAt(0)=='L' || s.charAt(0)=='l')
//                    con=2;
//                else if(s.charAt(0)=='C' || s.charAt(0)=='c')
//                    con=3;
//                else if(s.charAt(0)=='A' || s.charAt(0)=='a' || s.charAt(0)=='f' || s.charAt(0)=='F')
//                    con=4;
//
//                Intent intent = new Intent(Homepage.this, Images_display.class);
//                intent.putExtra(s_key,con);
//                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.myprofile)
        {
            startActivity(new Intent(MainActivity.this, User_Profile.class));
        }
        else if(item.getItemId()==R.id.search)
        {

        }
        else if(item.getItemId()==R.id.notify)
        {

        }
        else if(item.getItemId()==R.id.signout)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("key", 0);
            editor.apply();
            startActivity(new Intent(MainActivity.this,Login_page.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();

    }



}

