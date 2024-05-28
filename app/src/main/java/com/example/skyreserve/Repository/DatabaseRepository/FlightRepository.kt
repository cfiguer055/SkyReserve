package com.example.skyreserve.Repository.DatabaseRepository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.skyreserve.Database.Dao.FlightDao
import com.example.skyreserve.Database.Entity.Flight
import com.example.skyreserve.Database.SkyReserveDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightRepository  @Inject constructor(private val flightDao: FlightDao) {
//    private val flightDao: FlightDao
//    private val allFlights: LiveData<List<Flight>>
    // ... other properties

//    init {
//        val database = SkyReserveDatabase.getInstance(application)
//        flightDao = database.flightDao()
//        allFlights = flightDao.getAllFlightsFlow()
//            .flowOn(Dispatchers.IO)
//            .asLiveData()
//        // initialize other properties
//    }

    private val allFlights: LiveData<List<Flight>> = flightDao.getAllFlightsFlow()
        .flowOn(Dispatchers.IO)
        .asLiveData()

    //Repository Methods
    suspend fun insertFlight(flight: Flight) {
        flightDao.insertFlight(flight)
    }

    suspend fun updateFlight(flight: Flight) {
        flightDao.updateFlight(flight)
    }

    suspend fun deleteFlight(flight: Flight) {
        flightDao.deleteFlight(flight)
    }

    fun getAllFlights(): LiveData<List<Flight>> {
        return allFlights
    }
}