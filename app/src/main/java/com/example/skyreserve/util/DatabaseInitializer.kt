package com.example.skyreserve.util
//
//import android.content.Context
//import androidx.room.Room
//import com.example.skyreserve.Database.SkyReserveDatabase
//
//class DatabaseInitializer private constructor() {
//
//    companion object {
//        private var instance: DatabaseInitializer? = null
//
//        fun getInstance(context: Context): DatabaseInitializer {
//            return instance ?: synchronized(this) {
//                instance ?: DatabaseInitializer().also { instance = it }
//            }
//        }
//    }
//
//    fun initializeDatabase(context: Context) {
//        // Initialize your database here
//        val database = Room.databaseBuilder(
//            context.applicationContext,
//            SkyReserveDatabase::class.java,
//            "skyreserve_database"
//        ).build()
//    }
//}