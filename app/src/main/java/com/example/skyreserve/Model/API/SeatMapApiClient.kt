package com.example.skyreserve.Model.API

import com.example.skyreserve.Model.EnhancedSeatMapRequest
import com.example.skyreserve.Model.EnhancedSeatMapResponse
import retrofit2.Call

class SeatMapApiClient {
    private val apiService: SeatMapApiService = TODO()

    init {
        // Initialize Retrofit and create apiService
    }

    fun fetchEnhancedSeatMap(request: EnhancedSeatMapRequest): Call<EnhancedSeatMapResponse> {
        return apiService.getEnhancedSeatMap(request)
    }
}