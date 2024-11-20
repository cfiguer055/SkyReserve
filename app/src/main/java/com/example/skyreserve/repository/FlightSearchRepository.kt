package com.example.skyreserve.repository
import android.content.ContentValues
import android.util.Log
import com.example.skyreserve.BuildConfig
import com.example.skyreserve.api.ApiClient


import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class FlightSearchRepository(private val apiClient: ApiClient) {

//    companion object {
//        const val API_KEY: String = "TOP_SECRET_API_KEY"
//    }
    companion object {
        const val TAG = "FlightSearchRepository"
    }



    suspend fun getAirportDepartures(departAirportCode: String): ApiClient.ApiResponse {
        Log.d(ContentValues.TAG, "getAirportDepartures: $departAirportCode")
        Log.d(ContentValues.TAG, "Start DateTime: ${getCurrentDateTimeISO()}")
        Log.d(ContentValues.TAG, "End DateTime: ${getEndDateTimeISO()}")
//        Log.d(ContentValues.TAG, "API Key: ${BuildConfig.API_KEY}")

        val endpoint = "airports/$departAirportCode/flights/scheduled_departures"
        val queryParams = mapOf(
            // temp values
            "start" to getCurrentDateTimeISO(),
            "end" to getEndDateTimeISO(),
            "max_pages" to "1"
        )
//        val queryParams = mapOf(
//            "departure_airport" to departAirportCode,
//            "arrival_airport" to arriveAirportCode
//        )

        val headers = mapOf(
            "Accept" to "application/json; charset=UTF-8",
            "x-apikey" to BuildConfig.API_KEY // Replace with your actual API key
        )

        val response = apiClient.getAsync(endpoint, queryParams, headers)
        Log.d(ContentValues.TAG, "getAirportDepartures: $response")
        return response

        // return apiClient.get(endpoint, queryParams, headers)
    }

    /**
     * Encodes the time portion of an ISO 8601 date-time string by replacing ':' with '%3A'.
     *
     * @param dateTime The ISO 8601 date-time string (e.g., "2024-11-20T19:59:59Z").
     * @return The encoded date-time string (e.g., "2024-11-20T19%3A59%3A59Z").
     */
//    private fun encodeTimeInDateTime(dateTime: String): String {
//        return dateTime.replace(":", "%3A")
//    }
    /**
     * Returns the current date-time in ISO 8601 format without fractional seconds and with encoded colons.
     */
    private fun getCurrentDateTimeISO(): String {
        val now = ZonedDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS)
        val isoString = now.format(DateTimeFormatter.ISO_INSTANT)
        return isoString
        //return encodeTimeInDateTime(isoString)
    }
    /**
     * Returns the end date-time (1 day from now) in ISO 8601 format without fractional seconds and with encoded colons.
     */
    private fun getEndDateTimeISO(): String {
        val endDate = ZonedDateTime.now(ZoneId.of("UTC")).plusDays(1).truncatedTo(ChronoUnit.SECONDS)
        val isoString = endDate.format(DateTimeFormatter.ISO_INSTANT)
        return isoString
        //return encodeTimeInDateTime(isoString)
    }

    // Add more methods for other API endpoints

}

/*
curl -X GET "https://aeroapi.flightaware.com/aeroapi/airports/ICAO/flights/scheduled_departures?start=2021-12-31T19%3A59%3A59Z&end=2021-12-31T19%3A59%3A59Z&max_pages=1" \
 -H "Accept: application/json; charset=UTF-8" \
 -H "x-apikey: API_KEY"
*/