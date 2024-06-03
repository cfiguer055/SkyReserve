package com.example.skyreserve.ui.reservation_confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skyreserve.database.room.dao.ReservationDao
import com.example.skyreserve.WhatToDo.DataTransferObject.ReservationDetail

class ReservationViewModel (private val reservationDao: ReservationDao) : ViewModel() {

    fun getReservationWithPrice(reservationId: Long): LiveData<ReservationDetail> {
        return reservationDao.getReservationWithDetails(reservationId)
    }

    // ... other ViewModel code
}