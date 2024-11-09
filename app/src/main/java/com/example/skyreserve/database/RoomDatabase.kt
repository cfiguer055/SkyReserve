package com.example.skyreserve.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skyreserve.database.room.dao.FlightDao
import com.example.skyreserve.database.room.dao.ReservationDao
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.BaseFlightPrice
import com.example.skyreserve.database.room.entity.Flight
import com.example.skyreserve.database.room.entity.Reservation
import com.example.skyreserve.database.room.entity.Taxes
import com.example.skyreserve.database.room.entity.UserAccount

@Database(entities = [UserAccount::class, Reservation::class, Flight::class, BaseFlightPrice::class, Taxes::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {

    abstract fun userAccountDao(): UserAccountDao
    abstract fun reservationDao(): ReservationDao
    abstract fun flightDao(): FlightDao

    companion object {
        const val DATABASE_NAME = "sky_reserve_db"
    }

}
