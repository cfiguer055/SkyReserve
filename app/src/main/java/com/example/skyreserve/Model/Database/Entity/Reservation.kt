package com.example.skyreserve.Model.Database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reservationId: Long,
    val departueFlightNumber: String,
    val arrivalFlightNumber: String,
    val returnDepartueFlightNumber: String,
    val returnArrivalFlightNumber: String,
    val totalPrice: Double,
    val departureBasePrice: Double,
    val returnBasePrice: Double,
    // Other properties
)