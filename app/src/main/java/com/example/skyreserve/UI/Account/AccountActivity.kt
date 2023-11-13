package com.example.skyreserve.UI.Account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}