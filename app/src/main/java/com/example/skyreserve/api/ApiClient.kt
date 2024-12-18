package com.example.skyreserve.api

import android.util.Log
import com.example.skyreserve.model.FlightResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient(private val baseUrl: String = DEFAULT_BASE_URL) {

    private val client: OkHttpClient
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val flightResponseAdapter: JsonAdapter<FlightResponse> = moshi.adapter(FlightResponse::class.java)


    companion object {
        const val DEFAULT_BASE_URL = "https://aeroapi.flightaware.com/aeroapi"
        const val TAG = "ApiClient"
    }


    init {

        // Initialize OkHttpClient with Logging Interceptor and Timeouts
        val logging = HttpLoggingInterceptor { message -> Log.d(TAG, message) }
        logging.level = HttpLoggingInterceptor.Level.BODY

        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS) // Set appropriate timeouts
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Performs an HTTP GET request asynchronously using coroutines.
     *
     * @param endpoint The API endpoint.
     * @param queryParams A map of query parameters.
     * @param headers A map of HTTP headers.
     * @return An ApiResponse containing the response details.
     */
    suspend fun getAsync(
        endpoint: String,
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): ApiResponse<FlightResponse> = withContext(Dispatchers.IO) {
        val url = buildUrl(endpoint, queryParams)
        Log.d(TAG, "GET request URL: $url")

        val requestBuilder = Request.Builder().url(url)

        // Add custom headers to the request builder.
        for ((key, value) in headers) {
            if (key.equals("x-apikey", ignoreCase = true)) {
                Log.d(TAG, "Adding header: $key: [HIDDEN]") // Hide sensitive info
            } else {
                Log.d(TAG, "Adding header: $key: $value")
            }
            requestBuilder.header(key, value)
        }

        val request = requestBuilder.build()
        Log.d(TAG, "Request built successfully")

        executeRequest(request)
    }

    /**
     * Builds the complete URL with query parameters using HttpUrl.Builder.
     *
     * @param endpoint The API endpoint.
     * @param queryParams A map of query parameters.
     * @return The complete URL as a string.
     */
    private fun buildUrl(endpoint: String, queryParams: Map<String, String>): String {
        val httpUrlBuilder = baseUrl.toHttpUrlOrNull()?.newBuilder()
            ?.addPathSegments(endpoint)
            ?: throw IllegalArgumentException("Invalid base URL or endpoint")

        // Add query parameters with proper encoding
        for ((key, value) in queryParams) {
            httpUrlBuilder.addQueryParameter(key, value)
        }

        val url = httpUrlBuilder.build().toString()
        Log.d(TAG, "Built URL: $url")
        return url
    }

    /**
     * Executes an HTTP request and processes the response.
     *
     * @param request The HTTP request to execute.
     * @return An ApiResponse containing the parsed FlightResponse or error details.
     */
    private fun executeRequest(request: Request): ApiResponse<FlightResponse> {
        Log.d(TAG, "Executing request: ${request.url}")

        return try {
            Log.d(TAG, "About to execute the network call")
            client.newCall(request).execute().use { response ->
                Log.d(TAG, "Network call completed")
                val responseBody = response.body?.string()

                if (!response.isSuccessful) {
                    Log.e(TAG, "Unsuccessful response: ${response.code}, body: $responseBody")
                    // Extract meaningful error message if available
                    val errorMsg = extractErrorMessage(responseBody)
                    return@use ApiResponse(false, response.code, null, "Unsuccessful response", errorMsg)
                }

                val flightResponse = flightResponseAdapter.fromJson(responseBody!!)
                if (flightResponse != null) {
                    Log.d(TAG, "Response parsed successfully")
                    ApiResponse(true, response.code, flightResponse, null, null)
                } else {
                    Log.e(TAG, "Failed to parse response")
                    ApiResponse(false, response.code, null, "Failed to parse response", "Failed to parse response")
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Request execution failed", e)
            ApiResponse(false, -1, null, e.message, e.message)
        }
    }

    /**
     * Extracts a meaningful error message from the response body.
     *
     * @param responseBody The raw response body as a string.
     * @return A user-friendly error message.
     */
    private fun extractErrorMessage(responseBody: String?): String {
        // Implement parsing of the error message from the responseBody if the API provides structured error responses.
        // For now, return a generic message or the raw response.
        return responseBody ?: "Unknown error occurred."
    }


    /**
     * Data class representing the API response.
     */
    data class ApiResponse<T>(
        val isSuccess: Boolean,
        val statusCode: Int,
        val data: T?,
        val responseBody: String?,
        val errorMessage: String? ) {
    }
}
