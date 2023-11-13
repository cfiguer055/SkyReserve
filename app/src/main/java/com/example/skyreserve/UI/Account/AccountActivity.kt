package com.example.skyreserve.UI.Account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.UI.Welcome.WelcomeActivity
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var logger: UserInteractionLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logger = (application as MyApp).logger


        binding.logoutButton.setOnClickListener {
            navigateToSignIn()
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

    private fun navigateToSignIn() {
        val intent = Intent(this, WelcomeActivity::class.java)
        // temp intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        //finish() temp
    }
}