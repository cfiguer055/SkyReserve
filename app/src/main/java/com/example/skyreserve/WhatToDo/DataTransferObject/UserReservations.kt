package com.example.skyreserve.WhatToDo.DataTransferObject

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skyreserve.database.room.entity.Reservation
import com.example.skyreserve.database.room.entity.UserAccount

data class UserReservations(
    @Embedded val userAccount: UserAccount,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id",
        entity = Reservation::class
    )
    val reservations: List<ReservationDetail>
)