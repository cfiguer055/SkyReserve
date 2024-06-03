package com.example.skyreserve.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reservation_id")
    val reservationId: Long,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "departure_flight_number")
    val departureFlightNumber: String,
    @ColumnInfo(name = "return_flight_number")
    val returnFlightNumber: String,

    @ColumnInfo(name = "reservation_total")
    val reservationTotal: Double,

    @ColumnInfo(name = "departure_base_price")
    val departureBasePrice: Double,
    @ColumnInfo(name = "return_base_price")
    val returnBasePrice: Double,

    @ColumnInfo(name = "service_fee")
    val skyreserve_fee: Double,
)
