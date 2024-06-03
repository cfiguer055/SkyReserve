package com.example.skyreserve.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.skyreserve.database.room.dao.ReservationDao
import com.example.skyreserve.database.room.entity.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepository @Inject constructor(private val reservationDao: ReservationDao) {
//    private val reservationDao: ReservationDao
//    private val allReservations: LiveData<List<Reservation>>
//    // ... other properties
//
//    init {
//        val database = SkyReserveDatabase.getInstance(application)
//        reservationDao = database.reservationDao()
//        allReservations = reservationDao.getAllReservationsFlow()
//            .flowOn(Dispatchers.IO)
//            .asLiveData()
//        // initialize other properties
//    }

    private val allReservations: LiveData<List<Reservation>> = reservationDao.getAllReservationsFlow()
        .flowOn(Dispatchers.IO)
        .asLiveData()



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