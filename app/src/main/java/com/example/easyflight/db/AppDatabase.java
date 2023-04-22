package com.example.easyflight.db;

import android.os.Parcelable;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.easyflight.Flight;
import com.example.easyflight.FlightAndUserEntity;
import com.example.easyflight.User;

@Database(entities = {User.class, Flight.class, FlightAndUserEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public static final String USER_TABLE = "USER_TABLE";

    public static final String FLIGHTS_TABLE = "FLIGHTS_TABLE";

    public static final String BOOKING_TABLE = "BOOKING_TABLE";

    public abstract UserDAO getUserDAO();

    public abstract FlightsDAO getFlightsDAO();

    public abstract FlightToUserDAO getFlightAndUserDAO();
}
