package com.example.skyreserve.Database.DataTransferObject

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skyreserve.Database.Entity.Reservation
import com.example.skyreserve.Database.Entity.UserAccount

data class UserReservations(
    @Embedded val userAccount: UserAccount,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id",
        entity = Reservation::class
    )
    val reservations: List<ReservationDetail>
)