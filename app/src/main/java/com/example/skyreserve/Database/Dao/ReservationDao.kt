package com.example.skyreserve.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skyreserve.Database.DataTransferObject.ReservationDetail
import com.example.skyreserve.Database.Entity.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: Reservation)

    @Update
    suspend fun updateReservation(reservation: Reservation)

    @Delete
    suspend fun deleteReservation(reservation: Reservation)

    @Transaction
    @Query("SELECT * FROM reservations WHERE reservation_id = :reservationId")
    fun getReservationWithDetails(reservationId: Long): LiveData<ReservationDetail>

    @Query("""
    SELECT total_price FROM base_flight_prices
    WHERE reservation_id = :reservationId AND flight_type = 'departure'
    """)
    fun getDepartureBasePrice(reservationId: Long): LiveData<Double>

    @Query("""
    SELECT total_price FROM base_flight_prices
    WHERE reservation_id = :reservationId AND flight_type = 'return'
""")
    fun getReturnBasePrice(reservationId: Long): LiveData<Double>


    @Query("SELECT * FROM reservations")
    fun getAllReservationsFlow(): Flow<List<Reservation>>

    @Query("SELECT * FROM reservations WHERE reservation_id = :reservationId")
    suspend fun getReservationById(reservationId: Long): Reservation?

    // Add more queries as needed for your app's requirements
}