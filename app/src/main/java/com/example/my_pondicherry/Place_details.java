package com.example.my_pondicherry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_pondicherry.Adapter.PostAdapter;
import com.example.my_pondicherry.Model.Post;
import com.example.my_pondicherry.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import android.widget.HorizontalScrollView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.HorizontalScrollView;


public class Place_details extends AppCompatActivity {
//    private ViewGroup dataContainer;
    private HorizontalScrollView horizontalScrollView;
    public static  final  String KEY = "place";
    private Handler handler;
    private int currentImageIndex = 350;
    private int[] imageResources = {R.drawable.r1, R.drawable.goldenglobe, R.drawable.banyan};
    Button readmore,add,ticket,loc;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore,db;
    Animation topAnim,bottomAnim,left,right;
    private RecyclerView mRecyclerView;
    private FirebaseAuth auth;
    private StorageReference storageReference;
    private String Uid;
    ImageView post,download,share;
    private PostAdapter adapter;
    private List<Post> list;
    private Query query;
    private ListenerRegistration listenerRegistration;
    private List<Users> usersList;
    private EditText comnt;
    private static final int REQUEST_CODE=1;
    int num;
    private ImageView post2,post3;
    SwipeRefreshLayout swipeRefreshLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details);

        Intent intent = getIntent();
        String place = intent.getStringExtra(Tourist_places.KEY);

        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        handler = new Handler(Looper.getMainLooper());
//        share = findViewById(R.id.share);
        post = findViewById(R.id.post);
        loc = findViewById(R.id.loc);
        post2 = findViewById(R.id.post2);
        post3 = findViewById(R.id.post3);
//        download = findViewById(R.id.download);
        readmore = findViewById(R.id.readmore);
        add = findViewById(R.id.add);
        ticket = findViewById(R.id.tickets);
        comnt = findViewById(R.id.comnt);
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Uid = auth.getCurrentUser().getUid();

        //find place and initialize the array of places

        if(place.equals("rockbeach"))
        {
            post.setImageResource(R.drawable.r1);
            post2.setImageResource(R.drawable.r2);
            post3.setImageResource(R.drawable.r3);
        }
        else if(place.equals("para")) {

            post.setImageResource(R.drawable.paragliding);
            post2.setImageResource(R.drawable.para2);
            post3.setImageResource(R.drawable.para3);
        }
        else if(place.equals("scooba"))
        {
            post.setImageResource(R.drawable.scooba);
            post2.setImageResource(R.drawable.sc2);
            post3.setImageResource(R.drawable.sc3);
        }
        else if(place.equals("paradise"))
        {
            post.setImageResource(R.drawable.paradise);
            post2.setImageResource(R.drawable.b2);
            post3.setImageResource(R.drawable.b3);

        }
        else if(place.equals("aroville"))
        {
            post.setImageResource(R.drawable.goldenglobe);
            post2.setImageResource(R.drawable.goldenglobe);
            post3.setImageResource(R.drawable.goldenglobe);
        }
        else if(place.equals("botanical"))
        {
            post.setImageResource(R.drawable.botanical);
            post2.setImageResource(R.drawable.r2);
            post3.setImageResource(R.drawable.r3);
        }
        else if(place.equals("mangroo"))
        {
            post.setImageResource(R.drawable.pichavaram);
            post2.setImageResource(R.drawable.r2);
            post3.setImageResource(R.drawable.r3);
        }

        startAutoScroll();
//        full_screen();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = comnt.getText().toString().trim();
                add_comment(comment,place);
                comnt.setText("");
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = "11.923369711602506";
                String longitude = "79.83452070596606";
                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude+ " (" + "Location" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Place_details.this, Payment.class);
                intent.putExtra(KEY,"rockbeach");
                startActivity(intent);
            }
        });


        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Place_details.this, Readmore.class);
                intent.putExtra(KEY,"rockbeach");
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Place_details.this));
        list = new ArrayList<>();
        usersList = new ArrayList<>();
        adapter = new PostAdapter(Place_details.this , list, usersList,getApplicationContext());

        mRecyclerView.setAdapter(adapter);
        query = firestore.collection("places").whereEqualTo("place",place);
        Toast.makeText(Place_details.this,place,Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setAnimation(left);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
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

    public void share_Image()
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) post.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Uri uri = getImageToShare(bitmap);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent,"Share Image"));

    }

    public Uri getImageToShare(Bitmap bitmap)
    {
        File folder = new File(getCacheDir(),"images");
        Uri uri = null;
        try {
            folder.mkdir();
            File file = new File(folder, "shared_image.jpg");
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            uri = FileProvider.getUriForFile(this,"com.example.mylandcom",file);

        }
        catch (Exception e)
        {
            Toast.makeText(this," "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return uri;
    }


    private void  saveImage()
    {
        Uri images;
        ContentResolver contentResolver =  getContentResolver();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q)
        {
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }
        else
        {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,System.currentTimeMillis()+".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"images/*");
        Uri uri = contentResolver.insert(images,contentValues);

        try {

            BitmapDrawable  bitmapDrawable = (BitmapDrawable) post.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            OutputStream outputStream = null;
            try {
                outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Objects.requireNonNull(outputStream);

            Toast.makeText(Place_details.this,"image saved successfully",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void add_comment(String comment, String place)
    {
        if(comment.isEmpty())
        {
            Toast.makeText(Place_details.this, "Add comment to submit", Toast.LENGTH_SHORT).show();

        }
        else
        {
            String id = UUID.randomUUID().toString();
            String Userid = auth.getCurrentUser().getUid();
            Map<String,Object> doc = new HashMap<>();
            doc.put("comment",comment);
            doc.put("place", place);
            doc.put("time", FieldValue.serverTimestamp());
            doc.put("userid",Userid);

            db.collection("places").document(id).set(doc)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Place_details.this, "working", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Place_details.this, "not working", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void refresh()
    {
        listenerRegistration = query.addSnapshotListener(Place_details.this, new EventListener<QuerySnapshot>() {
//            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        String postId = doc.getDocument().getId();
                        Post post = doc.getDocument().toObject(Post.class).withId(postId);
                        String postUserId = doc.getDocument().getString("userid");
                        firestore.collection("Users").document(postUserId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()){
                                            Users users = task.getResult().toObject(Users.class);
                                            usersList.add(users);
                                            list.add(post);
                                            adapter.notifyDataSetChanged();
                                        }else{
                                            Toast.makeText(Place_details.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

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
            startActivity(new Intent(Place_details.this ,Login_page.class)); //mainactivity badhulu login ki marchali
            finish();
        }else{
            String currentUserId = firebaseAuth.getCurrentUser().getUid();
            firestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        if (!task.getResult().exists()){
                            startActivity(new Intent(Place_details.this , User_info.class));  // user profile ki marchali
                            finish();
                        }
                    }
                }
            });
        }

    }

    private void startAutoScroll() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Scroll to the next image
                horizontalScrollView.scrollTo(currentImageIndex * 1100, 0); // Assuming image width is 200dp
                currentImageIndex = (currentImageIndex + 1) % 3;               // Delay for 3 seconds
                handler.postDelayed(this, 3000);
            }
        }, 3000); // Initial delay of 3 seconds
}

}
