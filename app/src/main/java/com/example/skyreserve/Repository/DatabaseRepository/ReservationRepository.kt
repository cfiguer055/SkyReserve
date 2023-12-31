package com.example.skyreserve.Repository.DatabaseRepository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.skyreserve.Database.Dao.ReservationDao
import com.example.skyreserve.Database.Entity.Reservation
import com.example.skyreserve.Database.SkyReserveDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class ReservationRepository(application: Application) {
    private val reservationDao: ReservationDao
    private val allReservations: LiveData<List<Reservation>>
    // ... other properties

    init {
        val database = SkyReserveDatabase.getInstance(application)
        reservationDao = database.reservationDao()
        allReservations = reservationDao.getAllReservationsFlow()
            .flowOn(Dispatchers.IO)
            .asLiveData()
        // initialize other properties
    }

    //Repository Methods
    suspend fun insertReservation(reservation: Reservation) {
        reservationDao.insertReservation(reservation)
    }

    suspend fun updateReservation(reservation: Reservation) {
        reservationDao.updateReservation(reservation)
    }

    suspend fun deleteReservation(reservation: Reservation) {
        reservationDao.deleteReservation(reservation)
    }

    fun getAllReservations(): LiveData<List<Reservation>> {
        return allReservations
    }
}