package com.example.skyreserve.UI.Account

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.UI.AuthViewModel
import com.example.skyreserve.UI.Home.UserAccountViewModel
import com.example.skyreserve.UI.Welcome.WelcomeActivity
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding

    @Inject
    lateinit var sessionManager: LocalSessionManager

    @Inject
    lateinit var logger: UserInteractionLogger

    private val authViewModel: AuthViewModel by viewModels()
    private val userAccountViewModel: UserAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access sessionManager from MyApp
        //sessionManager = (applicationContext as MyApp).sessionManager

        // Access logger from MyApp
        //logger = (application as MyApp).logger


        binding.logoutButton.setOnClickListener {
            logout()
        }

        binding.emailCurrentUserInteractionButton.setOnClickListener {
            //logger.sendLogToEmail(this)
        }

        binding.emailAllUserInteractionButton.setOnClickListener {
            //logger.sendAllLogsToEmail(this)
        }

        binding.emailUserListButton.setOnClickListener {
            //logger.sendUsernamesListToEmail(this)
        }
    }

    private fun logout() {
        // dont call this directly from sessionManager. use userviewmodel
        //sessionManager.logoutUser()

        authViewModel.logout()
        userAccountViewModel.clear()

        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}