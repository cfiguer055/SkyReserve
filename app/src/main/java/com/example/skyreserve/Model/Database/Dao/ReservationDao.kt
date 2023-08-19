package com.example.skyreserve.Model.Database.Dao

import androidx.room.*
import com.example.skyreserve.Model.Database.Entity.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: Reservation)

    @Update
    suspend fun updateReservation(reservation: Reservation)

    @Delete
    suspend fun deleteReservation(reservation: Reservation)

    @Query("SELECT * FROM reservations")
    fun getAllReservationsFlow(): Flow<List<Reservation>>

    @Query("SELECT * FROM reservations WHERE id = :id")
    suspend fun getReservationById(id: Long): Reservation?

    // Add more queries as needed for your app's requirements
}