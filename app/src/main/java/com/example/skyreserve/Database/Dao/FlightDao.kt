package com.example.skyreserve.Database.Dao

import androidx.room.*
import com.example.skyreserve.Database.Entity.Flight
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