package com.example.skyreserve.model

import java.io.Serializable

data class FlightInfo(
    val departureTime: String,          // e.g., "08:00"
    val arrivalTime: String,            // e.g., "10:00"
    val departureCity: String,          // e.g., "Los Angeles"
    val arrivalCity: String,            // e.g., "Denver"
    val departureAirportCode: String,   // e.g., "LAX"
    val arrivalAirportCode: String,     // e.g., "DEN"
    val duration: String,               // e.g., "5h 30m"
    val airline: String,                // e.g., "Spirit Airlines"
    val price: String,                  // e.g., "$500"
    val tripType: String                 // e.g., "Round Trip"
): Serializable

// Using Pacelable is more complex but faster and more optimized for Android
//import android.os.Parcelable
//import kotlinx.parcelize.Parcelize
//
//@Parcelize
//data class FlightInfo(
//    // Your properties here
//) : Parcelable