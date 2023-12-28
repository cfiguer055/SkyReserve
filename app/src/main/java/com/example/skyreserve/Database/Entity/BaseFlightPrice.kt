package com.example.skyreserve.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_flight_prices")
data class BaseFlightPrice(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "price_id")
    val priceId: Long,

    @ColumnInfo(name = "reservation_id")
    val reservationId: Long, // Links to Reservation

    @ColumnInfo(name = "total_price")
    val totalPrice: Double,

    @ColumnInfo(name = "flight_type")
    val flightType: String // "departure" or "return"
)
