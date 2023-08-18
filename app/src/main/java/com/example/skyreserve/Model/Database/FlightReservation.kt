package com.example.skyreserve.Model.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "flight_reservations")
data class FlightReservation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reservationId: Long,
    val departueFlightNumber: String,
    val arrivalFlightNumber: String,
    val returnDepartueFlightNumber: String,
    val returnArrivalFlightNumber: String,
    val totalPrice: BigDecimal,
    val departureBasePrice: BigDecimal,
    val returnBasePrice: BigDecimal,
    // Other properties
)