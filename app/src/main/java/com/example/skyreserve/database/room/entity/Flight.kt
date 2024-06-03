package com.example.skyreserve.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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