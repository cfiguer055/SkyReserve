package com.example.skyreserve.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taxes")
data class Taxes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tax_id")
    val taxId: Long,
    @ColumnInfo(name = "reservation_id")
    val reservationId: Long, // Links to Reservation

    @ColumnInfo(name = "tax_total")
    val taxTotal: Double,
    @ColumnInfo(name = "federal_tax")
    val federalTax: Double,
    @ColumnInfo(name = "airport_tax")
    val airportTax: Double,
    @ColumnInfo(name = "airline_tax")
    val airlineTax: Double,
)