package com.example.skyreserve.UI.ReservationConfirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skyreserve.Database.Dao.ReservationDao
import com.example.skyreserve.Database.DataTransferObject.ReservationDetail

class ReservationViewModel (private val reservationDao: ReservationDao) : ViewModel() {

    fun getReservationWithPrice(reservationId: Long): LiveData<ReservationDetail> {
        return reservationDao.getReservationWithDetails(reservationId)
    }

    // ... other ViewModel code
}