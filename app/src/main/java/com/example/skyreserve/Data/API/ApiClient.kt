package com.example.skyreserve.Data.API

// Import necessary packages from the OkHttp library and Java I/O.
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException



// Create a class named ApiClient which takes a baseUrl as a parameter.
class ApiClient(private val baseUrl: String) {
    // Initialize an instance of the OkHttpClient.
    private val client: OkHttpClient = OkHttpClient()

    companion object {
        const val DEFAULT_BASE_URL = "https://aeroapi.flightaware.com/aeroapi/"
    }

    // Overloaded constructor to use the default baseUrl if none is provided
    constructor() : this(DEFAULT_BASE_URL)


    // Define a method to perform an HTTP GET request.
    fun get(endpoint: String, queryParams: Map<String, String> = emptyMap(), headers: Map<String, String> = emptyMap()): ApiResponse {
        // Build the full URL including endpoint and query parameters.
        val url = buildUrl(endpoint, queryParams)

        // Create a new request builder with the specified URL.
        val requestBuilder = Request.Builder().url(url)

        // Add custom headers to the request builder.
        for((key, value) in headers) {
            requestBuilder.header(key, value)
        }

        // Create a new HTTP request using the OkHttp library.
        val request = requestBuilder.build()

        // Execute the request and return the response.
        return executeRequest(request)
    }


    // Add more methods for different HTTP methods like POST, PUT, DELETE, etc. (if necessary)



    // Method to build the complete URL with query parameters.
    private fun buildUrl(endpoint: String, queryParams: Map<String, String>): String {
        // Construct the query string from the provided query parameters.
        val query = queryParams.entries.joinToString("&") {(key, value) -> "$key=$value"}

        // Combine the baseUrl, endpoint, and query parameters to form the complete URL.
        return if (query.isNotEmpty()) {
            "$baseUrl/$endpoint?$query"
        } else {
            "$baseUrl/$endpoint"
        }
    }


    // Method to execute an HTTP request and process the response.
    private fun executeRequest(request: Request): ApiResponse {
        try {
            // Execute the request using the OkHttpClient and obtain the response.
            val response: Response = client.newCall(request).execute()

            // Create an ApiResponse object using information from the response.
            return ApiResponse(response.isSuccessful, response.code, response.body?.string())
        } catch (e: IOException) {
            // In case of an exception (e.g., network error), return an error ApiResponse.
            return ApiResponse(false, -1, e.message)
        }
    }


    // Define a data class to hold information about the API response.
    data class ApiResponse(val isSuccess: Boolean, val statusCode: Int, val responseBody: String?)
}
