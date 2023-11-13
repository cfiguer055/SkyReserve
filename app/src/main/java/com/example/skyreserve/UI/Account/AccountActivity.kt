package com.example.skyreserve.UI.Account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.UI.Welcome.WelcomeActivity
import com.example.skyreserve.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.logoutButton.setOnClickListener {
            navigateToSignIn()
        }
    }

    private fun navigateToSignIn() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}