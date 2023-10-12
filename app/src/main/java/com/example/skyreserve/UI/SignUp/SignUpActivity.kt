package com.example.skyreserve.UI.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skyreserve.Database.SkyReserveDatabase
import androidx.navigation.fragment.NavHostFragment
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository


/*
The activity manages the SignUpFragment.
It sets up the navigation flow for the sign-up process.
You can include any additional logic for initializing the app here.
*/
class SignUpActivity : AppCompatActivity() {

    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController

        // Initialize your database and UserAccountDao
        val skyReserveDatabase = SkyReserveDatabase.getInstance(applicationContext)
        val userAccountDao = skyReserveDatabase.userAccountDao()

        authRepository = AuthRepository(userAccountDao) // Initialize your auth repository

        // Other initialization code

        if (savedInstanceState == null) {
            val accountInfoFragment = AccountInfoFragment().apply {
                // Pass the authRepository to the fragment
                authRepository = this@SignUpActivity.authRepository
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, accountInfoFragment)
                .commitNow()
        }
    }
}
