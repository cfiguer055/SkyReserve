package com.example.skyreserve.UI.ReservationConfirmation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.databinding.ActivityReservationConfirmationBinding

class ReservationConfirmationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReservationConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}