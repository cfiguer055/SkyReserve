package com.example.skyreserve.App

import LocalSessionManager
import android.app.Application
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.SkyReserveDatabase
import com.example.skyreserve.Util.DatabaseInitializer
import com.example.skyreserve.Util.UserInteractionLogger

class MyApp : Application() {
    lateinit var logger: UserInteractionLogger
    lateinit var userAccountDao: UserAccountDao
    lateinit var sessionManager: LocalSessionManager

    override fun onCreate() {
        super.onCreate()

        // Initialize the Session Manager
        sessionManager = LocalSessionManager(this)

        // Set up SQL Database
        DatabaseInitializer.getInstance(this).initializeDatabase(this)

        // Initialize the DAO
        userAccountDao = SkyReserveDatabase.getInstance(this).userAccountDao()

        // Initialize the Logger
        logger = UserInteractionLogger(applicationContext)
    }
}

