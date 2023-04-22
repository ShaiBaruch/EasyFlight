package com.example.easyflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.easyflight.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.FLIGHTS_TABLE)
public class Flight {

    @PrimaryKey(autoGenerate = true)
    private long mFlightId;
    private String mOrigin;
    private String mDestination;
    private int mCapacity;
    private String mDate;
    private double mPrice;
    private int mSeats;

    public Flight(String origin, String destination, int capacity, int seats, String date, double price) {
        mOrigin = origin;
        mDestination = destination;
        mCapacity = capacity;
        mSeats = seats;
        mDate = date;
        mPrice = price;
    }

    public long getMFlightId() {
        return mFlightId;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public String getDestination() {
        return mDestination;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getSeats() {
        return mSeats;
    }

    public void setMFlightId(int mFlightId) {
        this.mFlightId = mFlightId;
    }

    public void setOrigin(String origin) {
        this.mOrigin = origin;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }

    public void setCapacity(int capacity) {
        this.mCapacity = capacity;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setSeats(int seats) {
        this.mSeats = seats;
    }

    @Override
    public String toString(){
        String output;
        output = "Flight "+ mFlightId+"\n";
        output += "From "+ mOrigin + " to " + mDestination +"\n";
        output += "Departing on " + mDate + "\n";
        output += "Price: $" + mPrice + ", Available seats: " + (mCapacity - mSeats);
        return output;
    }

}
