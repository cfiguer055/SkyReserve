package com.example.skyreserve.Model.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skyreserve.Model.Database.Entity.Flight
import com.example.skyreserve.Model.Database.Entity.Reservation
import com.example.skyreserve.Model.Database.Entity.UserAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlight(flight: Flight)

    @Update
    suspend fun updateFlight(flight: Flight)

    @Delete
    suspend fun deleteFlight(flight: Flight)

    @Query("SELECT * FROM flights")
    fun getAllFlightsFlow(): Flow<List<Flight>>

    @Query("SELECT * FROM flights WHERE id = :id")
    suspend fun getFlightById(id: Long): Flight?

    // Add more queries as needed for your app's requirements
}