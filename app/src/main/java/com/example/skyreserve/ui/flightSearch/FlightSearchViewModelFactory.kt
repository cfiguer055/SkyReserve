package com.example.skyreserve.ui.flightSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FlightSearchViewModelFactory() : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FlightSearchViewModel::class.java)) {
            return FlightSearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}