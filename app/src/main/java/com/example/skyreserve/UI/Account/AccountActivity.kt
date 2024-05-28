package com.example.skyreserve.UI.Account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.UI.Welcome.WelcomeActivity
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var sessionManager: LocalSessionManager
    private lateinit var logger: UserInteractionLogger

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
            logger.sendLogToEmail(this)
        }

        binding.emailAllUserInteractionButton.setOnClickListener {
            logger.sendAllLogsToEmail(this)
        }

        binding.emailUserListButton.setOnClickListener {
            //logger.sendUsernamesListToEmail(this)
        }
    }

    private fun logout() {
        sessionManager.logoutUser()

        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}