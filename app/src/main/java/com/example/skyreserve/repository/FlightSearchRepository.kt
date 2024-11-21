package com.example.skyreserve.repository
import android.content.ContentValues
import android.util.Log
import com.example.skyreserve.BuildConfig.API_KEY
import com.example.skyreserve.api.ApiClient
import com.example.skyreserve.model.Flight
import com.example.skyreserve.model.FlightInfo
import com.example.skyreserve.model.FlightResponse
import java.time.LocalDate


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



    suspend fun getAirportDepartures(departAirportCode: String, departureDate: String)
    : ApiClient.ApiResponse<FlightResponse> {
        Log.d(ContentValues.TAG, "getAirportDepartures: $departAirportCode")
        Log.d(ContentValues.TAG, "Start DateTime: ${getStartDateTimeISO(departureDate)}")
        Log.d(ContentValues.TAG, "End DateTime: ${getEndDateTimeISO(departureDate)}")
//        Log.d(ContentValues.TAG, "API Key: ${BuildConfig.API_KEY}")

        val endpoint = "airports/$departAirportCode/flights/scheduled_departures"
        val queryParams = mapOf(
            // temp values
            "start" to getStartDateTimeISO(departureDate),
            "end" to getEndDateTimeISO(departureDate),
            "max_pages" to "5"
        )
//        val queryParams = mapOf(
//            "departure_airport" to departAirportCode,
//            "arrival_airport" to arriveAirportCode
//        )

        val headers = mapOf(
            "Accept" to "application/json; charset=UTF-8",
            "x-apikey" to API_KEY // Replace with your actual API key
        )

        val response = apiClient.getAsync(endpoint, queryParams, headers)
        Log.d(ContentValues.TAG, "getAirportDepartures: $response")
        return response

        // return apiClient.get(endpoint, queryParams, headers)
    }

    /**
     * Maps a list of Flight objects to a list of FlightInfo domain models.
     *
     * @param flights The list of Flight objects from the API.
     * @return A list of FlightInfo domain models.
     */
    fun mapFlightsToFlightInfo(flights: List<Flight>): List<FlightInfo> {
        return flights.map { flight ->
            FlightInfo(
                departureTime = flight.scheduled_out?.substring(11, 16) ?: "Unknown", // Extract "HH:mm"
                arrivalTime = flight.scheduled_in?.substring(11, 16) ?: "Unknown", // Extract "HH:mm"
                departureCity = flight.origin?.city ?: "Unknown",
                arrivalCity = flight.destination?.city ?: "Unknown",
                departureAirportCode = flight.origin?.code_iata ?: "Unknown",
                arrivalAirportCode = flight.destination?.code_iata ?: "Unknown",
                duration = if (flight.scheduled_out != null && flight.scheduled_in != null) {
                    calculateDuration(flight.scheduled_out, flight.scheduled_in)
                } else {
                    "N/A" // Handle missing duration calculation
                },
                airline = flight.operator ?: "Unknown",
                price = "$500", // Placeholder: Replace with actual pricing logic if available
                tripType = "Round Trip" // Placeholder: Adjust based on actual trip type
            )
        }
    }

    /**
     * Calculates the flight duration based on scheduled departure and arrival times.
     *
     * @param scheduledOut The scheduled departure time in ISO 8601 format.
     * @param scheduledIn The scheduled arrival time in ISO 8601 format.
     * @return The flight duration as a string (e.g., "5h 30m").
     */
    private fun calculateDuration(scheduledOut: String, scheduledIn: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val departure = ZonedDateTime.parse(scheduledOut, formatter)
        val arrival = ZonedDateTime.parse(scheduledIn, formatter)
        val duration = java.time.Duration.between(departure, arrival)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        return "${hours}h ${minutes}m"
    }


    /**
     * Returns the start date-time (00:00 UTC) for the given departure date in ISO 8601 format.
     */
    private fun getStartDateTimeISO(departureDate: String): String {
        // val formatter = DateTimeFormatter.ofPattern("dd MMM").withZone(ZoneId.of("UTC"))
        val currentYear = ZonedDateTime.now(ZoneId.of("UTC")).year

        // Parse the date for the current year
        var parsedDate = LocalDate.parse("$departureDate $currentYear", DateTimeFormatter.ofPattern("dd MMM yyyy"))

        // If the parsed date is before today, use the next year
        if (parsedDate.isBefore(LocalDate.now(ZoneId.of("UTC")))) {
            parsedDate = parsedDate.plusYears(1)
        }

        // Create the ZonedDateTime for 00:00
        val startDateTime = parsedDate.atStartOfDay(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS)
        return startDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }
//    private fun getCurrentDateTimeISO(departureDate: String): String {
//        val now = ZonedDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS)
//        val isoString = now.format(DateTimeFormatter.ISO_INSTANT)
//        return isoString
//        //return encodeTimeInDateTime(isoString)
//    }
    /**
     * Returns the end date-time (23:59:59 UTC) for the given departure date in ISO 8601 format.
     */
    private fun getEndDateTimeISO(departureDate: String): String {
        //val formatter = DateTimeFormatter.ofPattern("dd MMM").withZone(ZoneId.of("UTC"))
        val currentYear = ZonedDateTime.now(ZoneId.of("UTC")).year

        // Parse the date for the current year
        var parsedDate = LocalDate.parse("$departureDate $currentYear", DateTimeFormatter.ofPattern("dd MMM yyyy"))

        // If the parsed date is before today, use the next year
        if (parsedDate.isBefore(LocalDate.now(ZoneId.of("UTC")))) {
            parsedDate = parsedDate.plusYears(1)
        }

        // Create the ZonedDateTime for 23:59:59
        val endDateTime = parsedDate.plusDays(1).atStartOfDay(ZoneId.of("UTC")).minusSeconds(1)
        return endDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }
//    private fun getEndDateTimeISO(departureDate: String): String {
//        val endDate = ZonedDateTime.now(ZoneId.of("UTC")).plusDays(1).truncatedTo(ChronoUnit.SECONDS)
//        val isoString = endDate.format(DateTimeFormatter.ISO_INSTANT)
//        return isoString
//        //return encodeTimeInDateTime(isoString)
//    }

    /**
     * Encodes the time portion of an ISO 8601 date-time string by replacing ':' with '%3A'.
     *
     * @param dateTime The ISO 8601 date-time string (e.g., "2024-11-20T19:59:59Z").
     * @return The encoded date-time string (e.g., "2024-11-20T19%3A59%3A59Z").
     */
//    private fun encodeTimeInDateTime(dateTime: String): String {
//        return dateTime.replace(":", "%3A")
//    }


    // Add more methods for other API endpoints

}

/*
curl -X GET "https://aeroapi.flightaware.com/aeroapi/airports/ICAO/flights/scheduled_departures?start=2021-12-31T19%3A59%3A59Z&end=2021-12-31T19%3A59%3A59Z&max_pages=1" \
 -H "Accept: application/json; charset=UTF-8" \
 -H "x-apikey: API_KEY"
*/