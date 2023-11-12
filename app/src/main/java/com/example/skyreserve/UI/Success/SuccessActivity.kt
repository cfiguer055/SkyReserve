package com.example.skyreserve.UI.Success

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.UI.Home.HomeActivity
import com.example.skyreserve.databinding.ActivitySuccessBinding

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okayButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}