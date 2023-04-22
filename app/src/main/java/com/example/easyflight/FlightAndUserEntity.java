package com.example.easyflight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.easyflight.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.BOOKING_TABLE, primaryKeys = {})
public class FlightAndUserEntity {

    @PrimaryKey(autoGenerate = true)
    public int mBookingId;
    private long mFlightId;
    private long mUserId;
    //private Date mDate;

    public FlightAndUserEntity(long flightId, long userId){
        mFlightId = flightId;
        mUserId = userId;
    }

    public long getMFlightId() {
        return mFlightId;
    }

    public void setMFlightId(int mFlightId) {
        this.mFlightId = mFlightId;
    }

    public long getMUserId() {
        return mUserId;
    }

    public void setMUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getMBookingId() {
        return mBookingId;
    }

    public void setMBookingId(int mBookingId) {
        this.mBookingId = mBookingId;
    }

    @Override
    public String toString() {
        return "Flight Details: Booking Id: " + mBookingId + " |, Flight Id: " + mFlightId + " | User Id: " + mUserId;
    }
}
