package com.example.skyreserve.Model.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "flights")
data class Flight(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val flightId: Long = 0,
    val departureDate: Date,
    val arrivalDate: Date,
    val departureTime: Time,
    val arrivalTime: Time,
    // Other properties
)