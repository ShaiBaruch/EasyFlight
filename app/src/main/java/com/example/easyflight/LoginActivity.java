package com.example.easyflight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.easyflight.db.AppDatabase;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mLogin;
    private Button mBackLoginToStart;

    private List<User> mUsers;

    private String mUsername;
    private String mPassword;
    private User mUser;

    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = ApplicationAssets.getDatabase();
        setContentView(R.layout.activity_login);

        wireUpDisplay();
    }

    private void wireUpDisplay(){
        mUsernameField = findViewById(R.id.userNameEditText);
        mPasswordField = findViewById(R.id.passwordEditText);
        mLogin = findViewById(R.id.signUpBtn);
        mBackLoginToStart = findViewById(R.id.BackLoginToStart);

        mLogin.setOnClickListener(v -> {
            getValuesFromDisplay();
            if(checkForUserInDatabase()){
                ApplicationAssets.login(mUser);
                Intent intent = LandingPage.getIntent(getApplicationContext(), mUser.getUserId());
                    startActivity(intent);
                }
            });
        mBackLoginToStart.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void getValuesFromDisplay(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
         mUser = database.getUserDAO().getUserByUsername(mUsername);
         if(mUser == null){
             Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
             return false;
         }

         if (!mUser.getPassword().equals(mPassword)) {
             Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
             return false;
         }

        return true;
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

}
