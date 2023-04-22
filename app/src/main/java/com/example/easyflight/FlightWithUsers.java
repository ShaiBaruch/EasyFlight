package com.example.easyflight;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class FlightWithUsers {
    @Embedded
    private Flight flight;

    @Relation(
            parentColumn = "mFlightId",
            entity = User.class,
            entityColumn = "mUserId",
            associateBy = @Junction(
                    value = FlightAndUserEntity.class,
                    parentColumn = "mFlightId",
                    entityColumn = "mUserId"
            ))
    private List<User> users;

}
