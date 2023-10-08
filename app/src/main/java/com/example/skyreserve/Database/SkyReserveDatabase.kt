package com.example.skyreserve.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.Dao.ReservationDao
import com.example.skyreserve.Database.Dao.FlightDao
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Database.Entity.Reservation
import com.example.skyreserve.Database.Entity.Flight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserAccount::class, Reservation::class, Flight::class], version = 1)
abstract class SkyReserveDatabase : RoomDatabase() {

    abstract fun userAccountDao(): UserAccountDao
    abstract fun reservationDao(): ReservationDao
    abstract fun flightDao(): FlightDao

    companion object {
        const val DATABASE_NAME = "sky_reserve_db"


        @Volatile
        private var instance: SkyReserveDatabase? = null

        fun getInstance(context: Context): SkyReserveDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SkyReserveDatabase {
            return Room.databaseBuilder(context, SkyReserveDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(roomDatabaseCallback)
                .build()
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Perform operations when the database is created (if needed)

                // Use Kotlin coroutines to perform asynchronous database population
                CoroutineScope(Dispatchers.IO).launch {
                    // Populate the database in a background thread
                    // For example, insert some initial data
                    val userDao = instance?.userAccountDao()


                    //THIS IS TEMPORARY UNTIL REAL DATA
                    val newUserAccount = UserAccount(
                        username = "john_doe",
                        password = "secretpassword",
                        firstName = "John",
                        lastName = "Doe",
                        // ... other properties
                    )


                    userDao?.insertUserAccount(newUserAccount)
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Perform operations when the database is opened (if needed)
            }
        }

    }
}
