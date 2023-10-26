package com.example.my_pondicherry.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_pondicherry.R;
import com.example.my_pondicherry.Model.Post;
import com.example.my_pondicherry.Model.Users;
import com.example.my_pondicherry.User_info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {


    private List<Post> mList;
    private List<Users> usersList;
    private Activity context;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    public ImageView img;
    public static final  String key = "PostAdapter";

    Context app_context;
    public PostAdapter(Activity context , List<Post> mList , List<Users> usersList, Context app_context){
        this.mList = mList;
        this.context = context;
        this.usersList = usersList;
        this.app_context = app_context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.each_post , parent , false);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post post = mList.get(position);
        long milliseconds = post.getTime().getTime();
        String date  = DateFormat.format("MM/dd/yyyy" , new Date(milliseconds)).toString();

        holder.setComment(post.getComment());
        holder.setPostDate(date);

        Users user = usersList.get(position);
        holder.setUsername(user.getUser_name());
        holder.setProfilePic(user.getImage());
        holder.setEmail(user.getEmail());

        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, User_info.class);
                intent.putExtra("user",user.getUser_name());
                intent.putExtra("profile",user.getImage());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("phone",user.getPhone());
                intent.putExtra("bio",user.getBio());
//                intent.putExtra("date",date);
                intent.putExtra("first",user.getFirst());
                intent.putExtra("last",user.getLast());
                intent.putExtra("comment",post.getComment());
                intent.putExtra("time",post.getTime());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profilePic ;
        TextView postUsername , postDate ,postEmail,postComment;
        View mView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            profilePic = mView.findViewById(R.id.profile_pic);
            postUsername = mView.findViewById(R.id.username_tv);
            postEmail = mView.findViewById(R.id.email_tv);
            postDate = mView.findViewById(R.id.date_tv);
            postComment = mView.findViewById(R.id.comment_tv);

        }

        public void setUsername(String user_name){
            postUsername.setText(user_name);
        }
        public void setEmail(String email){postEmail.setText(email);}
        public void setPostDate(String date){postDate.setText(date);}
        public void setComment(String comment){
            postComment.setText(comment);
        }
        public void setProfilePic(String urlProfile){Glide.with(context).load(urlProfile).into(profilePic);}


    }
}