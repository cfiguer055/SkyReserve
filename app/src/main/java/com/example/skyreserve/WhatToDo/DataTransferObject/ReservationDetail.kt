package com.example.skyreserve.WhatToDo.DataTransferObject

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skyreserve.database.room.entity.BaseFlightPrice
import com.example.skyreserve.database.room.entity.Reservation
import com.example.skyreserve.database.room.entity.Taxes

data class ReservationDetail(
    @Embedded val reservation: Reservation,

    @Relation(
        parentColumn = "reservation_id",
        entityColumn = "reservation_id",
        entity = BaseFlightPrice::class
    )
    val flightPrice: List<BaseFlightPrice>,

    @Relation(
        parentColumn = "reservation_id",
        entityColumn = "reservation_id",
        entity = Taxes::class
    )
    val taxes: Taxes
)
