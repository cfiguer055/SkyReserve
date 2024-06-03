package com.example.skyreserve.WhatToDo

import com.example.skyreserve.model.EnhancedSeatMapRequest
import com.example.skyreserve.model.EnhancedSeatMapResponse
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