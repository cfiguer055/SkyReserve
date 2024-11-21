// File: com/example/skyreserve/model/Airport.kt

package com.example.skyreserve.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Airport(
    val code: String,
    val code_icao: String,
    val code_iata: String,
    val code_lid: String?,
    val timezone: String,
    val name: String,
    val city: String,
    val airport_info_url: String
)
