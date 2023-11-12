package com.example.skyreserve.Model

import android.os.Parcelable
import java.io.Serializable

data class FlightInfo(
    val departureTime: String,
    val arrivalTime: String,
    val departureCity: String,
    val arrivalCity: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val flightDuration: String,
    val airline: String,
    val cost: String,
    val flightType: String
) : Serializable

// Using Pacelable is more complex but faster and more optimized for Android
//import android.os.Parcelable
//import kotlinx.parcelize.Parcelize
//
//@Parcelize
//data class FlightInfo(
//    // Your properties here
//) : Parcelable