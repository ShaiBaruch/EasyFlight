package com.example.easyflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.easyflight.db.AppDatabase;
import com.example.easyflight.db.UserDAO;

import java.util.Date;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private long mUserId;
    private String mUsername;
    private String mPassword;

    private boolean mAdmin;




    public User(String username, String password , boolean admin) {//
        mUsername = username;
        mPassword = password;
        mAdmin = admin;

    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmAdmin(boolean mAdmin) {
        this.mAdmin = mAdmin;
    }

    public boolean getAdmin() {
        return mAdmin;
   }
}
