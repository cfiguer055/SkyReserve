package com.example.skyreserve.Data.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.skyreserve.Model.*

interface SeatMapApiService {
    @POST("your-api-endpoint")
    fun getEnhancedSeatMap(@Body request: EnhancedSeatMapRequest): Call<EnhancedSeatMapResponse>

}