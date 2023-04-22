package com.example.easyflight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyflight.db.AppDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mRePasswordField;
    private Button mSignUp;
    private Button mBackToMain;

    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = ApplicationAssets.getDatabase();
        setContentView(R.layout.activity_signup);

        wireUpDisplay();




    }

    private void wireUpDisplay(){
        mUsernameField = findViewById(R.id.userNameEditText);
        mPasswordField = findViewById(R.id.passwordEditText);
        mRePasswordField = findViewById(R.id.RePasswordEditText);
        mSignUp = findViewById(R.id.signUpBtn);
        mBackToMain = findViewById(R.id.BackSignUpToStart);


        mSignUp.setOnClickListener(v -> {
            if(validateSignUp()) {
                String username = mUsernameField.getText().toString();
                String password = mPasswordField.getText().toString();
                database.getUserDAO().insert(new User(username,password,false));
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mBackToMain.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private boolean validateSignUp() {
        String username = mUsernameField.getText().toString();
        User user = database.getUserDAO().getUserByUsername(username);
        if(user != null){
            Toast.makeText(getApplicationContext(), " User name is already in use", Toast.LENGTH_SHORT).show();
            return false;
        }

        String password = mPasswordField.getText().toString();
        String rePassword = mRePasswordField.getText().toString();
        if(!password.equals(rePassword)){
            Toast.makeText(getApplicationContext(), " Password does not match!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}
