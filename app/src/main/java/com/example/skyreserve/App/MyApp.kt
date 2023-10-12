package com.example.skyreserve.App

import android.app.Application
import com.example.skyreserve.Utility.DatabaseInitializer

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Set up SQL Database
        DatabaseInitializer.getInstance(this).initializeDatabase(this)
    }
}
