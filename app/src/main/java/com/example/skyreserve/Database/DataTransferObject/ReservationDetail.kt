package com.example.skyreserve.Database.DataTransferObject

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skyreserve.Database.Entity.BaseFlightPrice
import com.example.skyreserve.Database.Entity.Reservation
import com.example.skyreserve.Database.Entity.Taxes

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
