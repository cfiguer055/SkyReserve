package com.example.skyreserve.WhatToDo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.skyreserve.model.*

interface SeatMapApiService {
    @POST("your-api-endpoint")
    fun getEnhancedSeatMap(@Body request: EnhancedSeatMapRequest): Call<EnhancedSeatMapResponse>

}