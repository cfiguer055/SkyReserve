package com.example.skyreserve.ui.seat_map
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.skyreserve.model.EnhancedSeatMapRequest
import com.example.skyreserve.model.EnhancedSeatMapResponse
import com.example.skyreserve.WhatToDo.SeatMapApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeatMapViewModel : ViewModel() {
    private val apiClient = SeatMapApiClient()

    fun fetchSeatMap(request: EnhancedSeatMapRequest): LiveData<EnhancedSeatMapResponse> {
        val responseLiveData = MutableLiveData<EnhancedSeatMapResponse>()

        val call = apiClient.fetchEnhancedSeatMap(request)
        call.enqueue(object : Callback<EnhancedSeatMapResponse> {
            override fun onResponse(call: Call<EnhancedSeatMapResponse>, response: Response<EnhancedSeatMapResponse>) {
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<EnhancedSeatMapResponse>, t: Throwable) {
                // Handle error
            }
        })

        return responseLiveData
    }
}
