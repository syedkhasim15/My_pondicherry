package com.example.my_pondicherry.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_pondicherry.Model.Post;
import com.example.my_pondicherry.Model.Users;
import com.example.my_pondicherry.R;
import com.example.my_pondicherry.User_info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.PostViewHolder> {


    private List<Users> usersList;
    private Activity context;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    public ImageView img;
    public static final  String key = "PostAdapter";

    Context app_context;
    public GuideAdapter(Activity context ,List<Users> usersList, Context app_context){
        this.context = context;
        this.usersList = usersList;
        this.app_context = app_context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.each_guide , parent , false);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Users user = usersList.get(position);
        holder.setUsername(user.getLast());
        holder.setProfilePic(user.getImage());
        holder.setEmail(user.getEmail());
        holder.setPhone(user.getPhone());
        holder.setExp(user.getExp());
        holder.setId(user.getUser_name());

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

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + user.getPhone()));

                // Check if there's an app to handle the dial action
                if (context.getPackageManager() != null) {
                    context.startActivity(intent);
                } else {
                    // Handle the case where there's no app to make the call
                    // You could display a message to the user or take other actions
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        ImageView profilePic ;
        TextView postName , postPhone,postExp,postId,postEmail,postComment;
        View mView;
        Button btn;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            profilePic = mView.findViewById(R.id.profile_pic);
            postName = mView.findViewById(R.id.username_tv);
            postEmail = mView.findViewById(R.id.email_tv);
            postPhone = mView.findViewById(R.id.phone_tv);
            postExp = mView.findViewById(R.id.Exp_tv);
            postId= mView.findViewById(R.id.Id_tv);
            btn = mView.findViewById(R.id.contact);
        }

        public void setUsername(String user_name){
            postName.setText(user_name);
        }
        public void setEmail(String email){postEmail.setText(email);}
        public void setPhone(String phone){postPhone.setText(phone);}
        public void setExp(String Exp){
            postExp.setText(Exp);
        }
        public void setId(String Id){postId.setText(Id);}
        public void setProfilePic(String urlProfile){Glide.with(context).load(urlProfile).into(profilePic);}


    }
}