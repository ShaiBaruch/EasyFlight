package com.example.easyflight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyflight.db.AppDatabase;
import com.example.easyflight.db.UserDAO;

import java.util.List;

public class FindFlightActivity extends AppCompatActivity {

    private EditText enterFlightId;
    private TextView mFindScrollView;

    private Button mBookFlightBtn;
    private Button mMyBookedFlightsBtn;
    private Button mBackFindToLandingBtn;
    private User mUser;
    private FlightAndUserEntity mBooking;
    private int mFlightId;
    private  int mBookingId;


    private List<Flight> mFlights;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = ApplicationAssets.getDatabase();
        setContentView(R.layout.activity_findflight);

        wireUpDisplay();
        refreshDisplay();
    }

    private void wireUpDisplay() {
        enterFlightId = findViewById(R.id.selectFlightbyIdEditText);
        mFindScrollView = findViewById(R.id.FindFlightsView);
        mFindScrollView.setMovementMethod(new ScrollingMovementMethod());
        mBookFlightBtn = findViewById(R.id.BookFlightBtn);
        mMyBookedFlightsBtn = findViewById(R.id.MyBookedFlightsBtn);
        mBackFindToLandingBtn = findViewById(R.id.BackFlightToLandingBtn);

        mMyBookedFlightsBtn.setOnClickListener(v -> {
            Intent intent = BookedFlightsActivity.getIntent(getApplicationContext());
            startActivity(intent);
        });

        mBookFlightBtn.setOnClickListener(v -> {
            if (enterFlightId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Flight ID must be entered", Toast.LENGTH_SHORT).show();
                return;
            }

            long flightId = Long.parseLong(enterFlightId.getText().toString().trim());
            if (validateFlightId(flightId)) {
                mUser = ApplicationAssets.getUser();
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

                alertBuilder.setMessage("Please Confirm Purchase ");

                alertBuilder.setPositiveButton(getString(R.string.yes),
                        (dialog, which) -> {
                            database.getFlightAndUserDAO().insert(new FlightAndUserEntity(flightId, mUser.getUserId()));
                        });
                alertBuilder.setNegativeButton(getString(R.string.no),
                        (dialog, which) -> {});
                alertBuilder.create().show();

                refreshDisplay();
            }
        });

        mBackFindToLandingBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private boolean validateFlightId(long flightId) {
        boolean exist = mFlights.stream().anyMatch(x -> flightId == x.getMFlightId());
        if (!exist)
            Toast.makeText(this, "The ID you entered is invalid.", Toast.LENGTH_SHORT).show();
        return exist;
    }

    @SuppressLint("SetTextI18n")
    private void refreshDisplay(){
        mFlights = database.getFlightsDAO().getAllFlights();

        if(mFlights.isEmpty()){
            mFindScrollView.setText("No flights to show at this Time");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(Flight log: mFlights){
            sb.append(log);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mFindScrollView.setText(sb.toString());
    }



    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, FindFlightActivity.class);
        return intent;
    }
}
