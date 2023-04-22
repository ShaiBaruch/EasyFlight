package com.example.easyflight;

import com.example.easyflight.db.AppDatabase;

public class ApplicationAssets {

    private static AppDatabase database;

    private static User user = null;

    public static void setDatabase(AppDatabase appDatabase) {
        if (null == database)
            database = appDatabase;
    }

    public static AppDatabase getDatabase() {
        return database;
    }


    public static User getUser() {
        return user;
    }
    public static void login(User value) {
        user = value;
    }

    public static void logout() {
        user = null;
    }
}
