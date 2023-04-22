package com.example.easyflight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.easyflight.databinding.ActivityMainBinding;
import com.example.easyflight.db.AppDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button mLogin_button;
    Button mSignUp_button;

    ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        String databaseName = "easy-flight-database";

        View view = mMainBinding.getRoot();
        setContentView(view);

        mLogin_button = mMainBinding.loginButton;
        mSignUp_button = mMainBinding.signUpButton;
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, databaseName).allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        db.getUserDAO().insert(new User("admin2", "admin2", true));
        db.getUserDAO().insert(new User("testuser1", "testuser1", false));

        ApplicationAssets.setDatabase(db);


        mLogin_button.setOnClickListener(v -> {
            Intent intent = LoginActivity.getIntent(getApplicationContext());
            startActivity(intent);
        });
        mSignUp_button.setOnClickListener(v -> {
            Intent intent = SignUpActivity.getIntent(getApplicationContext());
            startActivity(intent);
        });

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

}