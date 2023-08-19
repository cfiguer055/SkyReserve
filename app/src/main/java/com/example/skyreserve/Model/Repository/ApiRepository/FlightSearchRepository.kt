package com.example.skyreserve.Model.Repository.ApiRepository
import com.example.skyreserve.Model.API.ApiClient


class FlightSearchRepository {

    private val apiClient: ApiClient = ApiClient()

    companion object {
        const val API_KEY: String = "TOP_SECRET_API_KEY"
    }


    fun getAirportDepartures(airportCode: String): ApiClient.ApiResponse {
        val endpoint = "airports/$airportCode/flights/scheduled_departures"
        val queryParams = mapOf(
            // temp values
            "start" to "2021-12-31T19%3A59%3A59Z",
            "end" to "2021-12-31T19%3A59%3A59Z",
            "max_pages" to "1"
        )
        val headers = mapOf(
            "Accept" to "application/json; charset=UTF-8",
            "x-apikey" to API_KEY // Replace with your actual API key
        )

        return apiClient.get(endpoint, queryParams, headers)
    }

    // Add more methods for other API endpoints

}

/*
curl -X GET "https://aeroapi.flightaware.com/aeroapi/airports/ICAO/flights/scheduled_departures?start=2021-12-31T19%3A59%3A59Z&end=2021-12-31T19%3A59%3A59Z&max_pages=1" \
 -H "Accept: application/json; charset=UTF-8" \
 -H "x-apikey: API_KEY"
*/