package com.example.skyreserve.App

import android.app.Application
import com.example.skyreserve.Util.DatabaseInitializer
import com.example.skyreserve.Util.UserInteractionLogger

class MyApp : Application() {
    lateinit var logger: UserInteractionLogger

    override fun onCreate() {
        super.onCreate()

        // Set up SQL Database
//        DatabaseInitializer.getInstance(this).initializeDatabase(this)
        logger = UserInteractionLogger(applicationContext)
    }
}
