package com.example.skyreserve.app

import android.app.Application
//import com.example.skyreserve.Util.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
//    lateinit var logger: UserInteractionLogger
//    lateinit var userAccountDao: UserAccountDao
//    lateinit var sessionManager: LocalSessionManager

    override fun onCreate() {
        super.onCreate()

//        // Initialize the Session Manager
//        sessionManager = LocalSessionManager(this)
//
//        // Set up SQL Database
//        DatabaseInitializer.getInstance(this).initializeDatabase(this)
//
//        // Initialize the DAO
//        userAccountDao = SkyReserveDatabase.getInstance(this).userAccountDao()
//
//        // Initialize the Logger
//        logger = UserInteractionLogger(applicationContext)
    }
}

