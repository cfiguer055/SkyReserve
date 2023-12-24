package com.example.skyreserve.UI.Success

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.UI.Account.AccountActivity
import com.example.skyreserve.UI.Home.HomeActivity
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivitySuccessBinding

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessBinding
    private lateinit var logger: UserInteractionLogger

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        logger = (application as MyApp).logger
        logger.logInteraction("SuccessActivity:")
        //logger.sendLogToEmail(this)


        binding.okayButton.setOnClickListener {
            //val intent = Intent(this, HomeActivity::class.java)

            // temp
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            // finish() temp
        }
    }
}