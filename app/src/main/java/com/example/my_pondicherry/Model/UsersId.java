package com.example.my_pondicherry.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class UsersId
{
    @Exclude
    public String UsersId;

    public <T extends UsersId>  T withId (@NonNull final String id){
        this.UsersId = id;
        return (T) this;
    }
}
