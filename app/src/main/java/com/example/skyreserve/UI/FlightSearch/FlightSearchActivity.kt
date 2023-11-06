package com.example.skyreserve.UI.FlightSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.R
import com.example.skyreserve.databinding.ActivityFlightSearchBinding
import java.util.*

class FlightSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlightSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}