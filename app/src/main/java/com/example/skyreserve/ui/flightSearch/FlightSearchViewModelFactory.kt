package com.example.skyreserve.ui.flightSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.repository.FlightSearchRepository

class FlightSearchViewModelFactory(private val flightSearchRepository: FlightSearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlightSearchViewModel(flightSearchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
