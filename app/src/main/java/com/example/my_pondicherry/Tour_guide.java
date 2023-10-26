package com.example.my_pondicherry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.my_pondicherry.Adapter.GuideAdapter;
import com.example.my_pondicherry.Adapter.PostAdapter;
import com.example.my_pondicherry.Model.Post;
import com.example.my_pondicherry.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class Tour_guide extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore,db;
    Animation topAnim,bottomAnim,left,right;
    private RecyclerView mRecyclerView;
    private FirebaseAuth auth;
    private List<Users> usersList;
    SwipeRefreshLayout swipeRefreshLayout;
    private GuideAdapter adapter;
    private ListenerRegistration listenerRegistration;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Tour_guide.this));
        usersList = new ArrayList<>();
        adapter = new GuideAdapter(Tour_guide.this ,usersList,getApplicationContext());

        mRecyclerView.setAdapter(adapter);
        query = firestore.collection("Users").whereEqualTo("Guide","Yes");
        swipeRefreshLayout.setAnimation(left);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                usersList.clear();
                refresh();
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

        if (firebaseAuth.getCurrentUser() != null){

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean isBottom = !mRecyclerView.canScrollVertically(1);
//                    if (isBottom)
//                        Toast.makeText(Place_details.this, "Reached Bottom", Toast.LENGTH_SHORT).show();
                }
            });

//
//
//            query = firestore.collection("Posts").orderBy("time" , Query.Direction.DESCENDING);
//            query = firestore.collection("posts").orderBy("time" , Query.Direction.DESCENDING);
//            query = firestore.collection("posts").whereEqualTo("Img_type","House");
            refresh();

        }
    }

    public void refresh()
    {
        listenerRegistration = query.addSnapshotListener(Tour_guide.this, new EventListener<QuerySnapshot>() {
            //            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        String UserId = doc.getDocument().getId();
                        Users users = doc.getDocument().toObject(Users.class).withId(UserId);
                        usersList.add(users);
                        adapter.notifyDataSetChanged();

                    }else{
                        adapter.notifyDataSetChanged();
                    }
                }
                listenerRegistration.remove();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(Tour_guide.this ,Login_page.class)); //mainactivity badhulu login ki marchali
            finish();
        }else{
            String currentUserId = firebaseAuth.getCurrentUser().getUid();
            firestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        if (!task.getResult().exists()){
                            startActivity(new Intent(Tour_guide.this , User_info.class));  // user profile ki marchali
                            finish();
                        }
                    }
                }
            });
        }

    }
}