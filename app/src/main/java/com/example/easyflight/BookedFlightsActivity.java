package com.example.easyflight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easyflight.db.AppDatabase;

import java.util.List;
import java.util.stream.Collectors;

public class BookedFlightsActivity extends AppCompatActivity {

    private AppDatabase database;
    private TextView myBookedFlightsList;
    private EditText enterFlightIdField;
    private Button cancelFlightButton;
    private Button backBookedToMain;
    private long mFlightId;
    private int mBookingId;
    private User mUser;
    private List<Flight> mFlights;
    private List<FlightAndUserEntity> mBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = ApplicationAssets.getDatabase();
        mUser = ApplicationAssets.getUser();
        setContentView(R.layout.activity_bookedflights);

        wireUpDisplay();

    }

    private void wireUpDisplay(){
        myBookedFlightsList = findViewById(R.id.flightsBoardTextView);
        enterFlightIdField = findViewById(R.id.enterFlightIdToCancelEditText);
        cancelFlightButton = findViewById(R.id.cancelFlightBtn);
        backBookedToMain = findViewById(R.id.backBookedToMainBtn);
        mUser = ApplicationAssets.getUser();
        refreshDisplay();

        cancelFlightButton.setOnClickListener(v -> {
            if (enterFlightIdField.getText().toString().isEmpty()) {
                Toast.makeText(this, "Flight ID must be entered", Toast.LENGTH_SHORT).show();
                return;
            }
            mFlightId = Long.parseLong(enterFlightIdField.getText().toString());
            if (validateFlightId(mFlightId)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage("Are you sure? ");

                alertBuilder.setPositiveButton(getString(R.string.yes),
                        (dialog, which) -> {
                            database.getFlightAndUserDAO()
                                    .delete(mBookings.stream().filter(x -> x.getMFlightId() == mFlightId).findFirst().get());
                            refreshDisplay();

                        });
                alertBuilder.setNegativeButton(getString(R.string.no),
                        (dialog, which) -> {

                        });
                alertBuilder.create().show();

            }
        });

        backBookedToMain.setOnClickListener(v -> {
            Intent intent = LandingPage.getIntent(getApplicationContext(), mUser.getUserId());
            startActivity(intent);
        });

    }

    private boolean validateFlightId(long flightId) {
        boolean exist = mBookings.stream().anyMatch(x -> flightId == x.getMFlightId());
        if (!exist)
            Toast.makeText(this, "The ID you entered is invalid.", Toast.LENGTH_SHORT).show();
        return exist;
    }

    @SuppressLint("SetTextI18n")
    private void refreshDisplay(){
        mBookings = database.getFlightAndUserDAO().getUserBookings(mUser.getUserId());
        if(mBookings.isEmpty()){
            myBookedFlightsList.setText("No flights to show at this Time");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(FlightAndUserEntity log: mBookings){
            sb.append(log);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        myBookedFlightsList.setText(sb.toString());
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BookedFlightsActivity.class);
        return intent;
    }
}
