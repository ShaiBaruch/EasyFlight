package com.example.easyflight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.easyflight.Flight;
import com.example.easyflight.User;

import java.util.List;

@Dao
public interface FlightsDAO {
    @Insert
    long insert(Flight flight);

    @Update
    void update(Flight...flights);

    @Delete
    void delete(Flight...flights);

    @Query("SELECT * FROM " + AppDatabase.FLIGHTS_TABLE + " ORDER BY mDate DESC")
    List<Flight> getAllFlights();

    @Query("SELECT * FROM " + AppDatabase.FLIGHTS_TABLE + " WHERE mFlightId = :mFlightId")
    Flight getFlightByFlightId(long mFlightId);

}
