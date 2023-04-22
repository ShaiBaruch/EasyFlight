package com.example.easyflight;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easyflight.db.AppDatabase;
import com.example.easyflight.db.FlightsDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AdminActivity extends AppCompatActivity {

    private TextView mCurrentFlightsField;
    private EditText mFlightIdField;
    private EditText mFlightOriginField;
    private EditText mFlightDestinationField;
    private EditText mFlightCapacityField;
    private EditText mFlightTimeField;
    private EditText mFlightPriceField;
    private Button mAddFlight;
    private Button mRemoveFlight;
    private Button mBackAdminToMain;

    private int mFlightId;
    private String mFlightOrigin;
    private String mFlightDestination;
    private int mFlightCapacity;
    private int mSeats;
    private String mFlightTime;
    private double mFlightPrice;
    private Flight mFlight;

    private List<Flight> mFlights;
    private User mUser;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = ApplicationAssets.getDatabase();
        setContentView(R.layout.activity_admin);

        wireUpDisplay();
        refreshDisplay();
        mFlightTimeField.setOnClickListener(v -> {
            showTime();

        });
        mAddFlight.setOnClickListener(v -> {
            getValuesFromDisplay();
            if(checkValidFlightInfo()){
                database.getFlightsDAO().insert(new Flight(mFlightOrigin,mFlightDestination,mFlightCapacity,mSeats,
                        mFlightTime, mFlightPrice));
            }
            refreshDisplay();
        });

        mBackAdminToMain.setOnClickListener(v -> {
            onBackPressed();
        });

        mRemoveFlight.setOnClickListener(v -> {
            if (mFlightIdField.getText().toString().isEmpty()) {
                Toast.makeText(this, "Flight ID must be entered", Toast.LENGTH_SHORT).show();
                return;
            }
            if (validateFlightId()) {
                long id  = Long.parseLong(mFlightIdField.getText().toString());
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

                alertBuilder.setMessage("Are you sure? ");
                Flight f = database.getFlightsDAO().getFlightByFlightId(id);
                alertBuilder.setPositiveButton(getString(R.string.yes),
                        (dialog, which) ->  {
                            database.getFlightsDAO().delete(f);
                            refreshDisplay();
                        });
                alertBuilder.setNegativeButton(getString(R.string.no),
                        (dialog, which) -> {});
                alertBuilder.create().show();
            }
        });
    }


    private void wireUpDisplay() {
        mCurrentFlightsField = findViewById(R.id.currentFlightsView);
        mCurrentFlightsField.setMovementMethod(new ScrollingMovementMethod());
        mFlightIdField = findViewById(R.id.flightIdEditText);
        mFlightOriginField = findViewById(R.id.flightOriginEditText);
        mFlightDestinationField = findViewById(R.id.flightDestinationEditText);
        mFlightCapacityField = findViewById(R.id.capacityEditText);
        mFlightTimeField = findViewById(R.id.flightTimeEditText);
        mFlightPriceField = findViewById(R.id.PriceEditText);
        mAddFlight = findViewById(R.id.AddFlightBtn);
        mRemoveFlight = findViewById(R.id.RemoveFlightBtn);
        mBackAdminToMain = findViewById(R.id.BackAdmintoMainBtn);

        Random rand = new Random();
        mSeats = rand.nextInt(1)+1;

    }

    private void showTime(){
        DatePickerDialog mDatePicker;
        final int[] date = new int[3];
        mDatePicker = new DatePickerDialog(AdminActivity.this, (view, year, month, dayOfMonth) -> {
            mFlightTimeField.setText(month+"-"+dayOfMonth+"-"+year);
            date[0] = year;
            date[1] = month;
            date[2] = dayOfMonth;

        },date[0],date[1],date[2]);

        mDatePicker.show();
    }

    @SuppressLint("SetTextI18n")
    private void refreshDisplay(){
        mFlights = database.getFlightsDAO().getAllFlights();

        if(mFlights.isEmpty()){
            mCurrentFlightsField.setText("No flights to show at this Time");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(Flight log: mFlights){
            sb.append(log);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mCurrentFlightsField.setText(sb.toString());
    }

    private boolean checkValidFlightInfo() {
        if (TextUtils.isEmpty(mFlightOriginField.getText().toString()) ||
                TextUtils.isEmpty(mFlightDestinationField.getText().toString()) ||
                mFlightCapacityField.getText().toString().trim().isEmpty() ||
                TextUtils.isEmpty(mFlightTimeField.getText().toString()) ||
                mFlightPriceField.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, " All fields must be filled ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateFlightId() {
        long flightIdToDelete = Long.parseLong(mFlightIdField.getText().toString().trim());
        boolean exist = mFlights.stream().anyMatch(x -> flightIdToDelete == x.getMFlightId());
        if (!exist)
            Toast.makeText(this, "The ID you entered is invalid.", Toast.LENGTH_SHORT).show();
        return exist;
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }

    private void getValuesFromDisplay(){
        mFlightOrigin = mFlightOriginField.getText().toString();
        mFlightDestination = mFlightDestinationField.getText().toString();
        mFlightCapacity = Integer.parseInt(mFlightCapacityField.getText().toString());
        mFlightTime = mFlightTimeField.getText().toString();
        mFlightPrice = Double.parseDouble(mFlightPriceField.getText().toString());


    }


}
