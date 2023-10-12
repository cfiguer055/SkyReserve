package com.example.skyreserve.UI.SignIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skyreserve.Database.SkyReserveDatabase
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository

/*
The activity manages the SignInFragment.
It sets up the navigation flow for the sign-in process.
You can include any additional logic for initializing the app here.
*/
class SignInActivity : AppCompatActivity() {

    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize your database and UserAccountDao (MIGHT NOT NEED THIS...REPO ALREADY GETS INSTANCE)
        //VIEWMODEL HAS ACCESS TO REPO METHODS
        // MIGHT NEED IT BECAUSE OF VIEWMODEL...look into it later
        val skyReserveDatabase = SkyReserveDatabase.getInstance(applicationContext)
        val userAccountDao = skyReserveDatabase.userAccountDao()

        authRepository = AuthRepository(userAccountDao) // Initialize your auth repository

        // Other initialization code

        if (savedInstanceState == null) {
            val signInFragment = SignInFragment().apply {
                // Pass the authRepository to the fragment
                authRepository = this@SignInActivity.authRepository
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignInFragment())
                .commitNow()
        }
    }
}
