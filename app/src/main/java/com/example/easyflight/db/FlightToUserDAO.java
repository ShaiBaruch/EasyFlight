package com.example.easyflight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.easyflight.Flight;
import com.example.easyflight.FlightAndUserEntity;

import java.util.List;

@Dao
public interface FlightToUserDAO {

    @Insert
    void insert(FlightAndUserEntity...flightAndUserEntities);

    @Delete
    void delete(FlightAndUserEntity...flightAndUserEntities);

    @Query("SELECT * FROM " + AppDatabase.BOOKING_TABLE) // + " ORDER BY mDate DESC"
    List<FlightAndUserEntity> getAllBookings();

    @Query("SELECT * FROM " + AppDatabase.BOOKING_TABLE + " WHERE mBookingId = :mBookingId")
    FlightAndUserEntity getBookingByBookingId(int mBookingId);

    @Query("SELECT * FROM " + AppDatabase.BOOKING_TABLE + " WHERE mUserId = :userId")
    List<FlightAndUserEntity> getUserBookings(long userId);
}
