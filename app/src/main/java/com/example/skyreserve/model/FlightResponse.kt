// File: FlightResponse.kt
package com.example.skyreserve.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlightResponse(
    @Json(name = "scheduled_departures") val scheduledDepartures: List<Flight>
)
