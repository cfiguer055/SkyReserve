package com.example.skyreserve.UI.Activities

import android.app.Application
import com.example.skyreserve.Utility.DatabaseInitializer

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseInitializer.getInstance(this).initializeDatabase(this)
    }
}
