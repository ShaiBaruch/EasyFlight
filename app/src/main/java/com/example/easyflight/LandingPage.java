package com.example.easyflight;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.easyflight.db.UserDAO;
import com.example.easyflight.db.AppDatabase;

import java.util.List;

public class LandingPage extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.easyflight.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.easyflight.preferencesKey";

    private SharedPreferences mPreferences = null;

    private AppDatabase db;
    private User mUser;
    private Button adminButton;
    private Button mFindAFlightBtn;
    private Button mMyBookingsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        db = ApplicationAssets.getDatabase();

        wireUpDisplay();
        mUser = ApplicationAssets.getUser();
        addUserToPreference(mUser.getUserId());
        invalidateOptionsMenu();

        adminButton = findViewById(R.id.adminBtn);

        if(mUser.getAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
        }

        adminButton.setOnClickListener(v -> {
            Intent intent = AdminActivity.getIntent(getApplicationContext());
            startActivity(intent);
        });

        mMyBookingsBtn.setOnClickListener(v -> {
            Intent intent = BookedFlightsActivity.getIntent(getApplicationContext());
            startActivity(intent);
        });


    }

    private void wireUpDisplay() {

        mFindAFlightBtn = findViewById(R.id.findFlightBtn);
        mMyBookingsBtn = findViewById(R.id.bookedFlightsBtn);

        mFindAFlightBtn.setOnClickListener(v -> {
            Intent intent = FindFlightActivity.getIntent(getApplicationContext());
            startActivity(intent);
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(mUser == null) {
            MenuItem item = menu.findItem(R.id.userTitle);
            item.setTitle(mUser.getUsername());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void addUserToPreference(long userId) {
        if(mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(USER_ID_KEY, userId);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, which) -> {
                    ApplicationAssets.logout();
                    clearUserFromIntent();
                    clearUserFromPref();
                    Intent intent = MainActivity.getIntent(getApplicationContext());
                    startActivity(intent);
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                (dialog, which) -> {

                });
        alertBuilder.create().show();
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref(){
       addUserToPreference(-1);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutButton:
                logoutUser();

            case R.id.userTitle:
                item.setTitle(mUser.getUsername());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context, long userId) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }


}
