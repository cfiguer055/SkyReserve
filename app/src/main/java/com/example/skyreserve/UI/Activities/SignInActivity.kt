package com.example.skyreserve.UI.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.SkyReserveDatabase
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.UI.Fragment.SignInFragment

/*
The activity manages the SignInFragment.
It sets up the navigation flow for the sign-in process.
You can include any additional logic for initializing the app here.
*/
class SignInActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize your database and UserAccountDao
        val skyReserveDatabase = SkyReserveDatabase.getInstance(applicationContext)
        val userAccountDao = skyReserveDatabase.userAccountDao()

        authRepository = AuthRepository(userAccountDao) // Initialize your auth repository

        // Other initialization code

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignInFragment())
                .commitNow()
        }
    }
}
