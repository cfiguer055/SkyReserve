package com.example.skyreserve.Model.Database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "flights")
data class Flight(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val flightId: Long = 0,
    val departureDate: String,
    val arrivalDate: String,
    val departureTime: String,
    val arrivalTime: String,
    // Other properties
)