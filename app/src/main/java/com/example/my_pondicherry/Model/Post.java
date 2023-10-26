package com.example.my_pondicherry.Model;


import java.util.Date;

public class Post extends PostId {

    private String comment,userid;
    private Date time;

    public String getUser_id(){return userid;}
    public String getComment() {
        return comment;
    }
    public Date getTime() {
        return time;
    }
}