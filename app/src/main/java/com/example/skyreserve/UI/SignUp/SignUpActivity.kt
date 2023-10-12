package com.example.skyreserve.UI.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skyreserve.Database.SkyReserveDatabase
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository


/*
The activity manages the SignUpFragment.
It sets up the navigation flow for the sign-up process.
You can include any additional logic for initializing the app here.
*/
class SignUpActivity : AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize your database and UserAccountDao
        val skyReserveDatabase = SkyReserveDatabase.getInstance(applicationContext)
        val userAccountDao = skyReserveDatabase.userAccountDao()

        authRepository = AuthRepository(userAccountDao) // Initialize your auth repository

        // Other initialization code

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, SignUpFragment())
//                .commitNow()
//        }
    }
}
