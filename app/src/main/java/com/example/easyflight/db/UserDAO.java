package com.example.easyflight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.easyflight.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User...users);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :username AND mPassword = :password")
    User getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserID = :mUserID")
    User getUserByUserId(int mUserID);



}
