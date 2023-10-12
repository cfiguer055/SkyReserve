package com.example.skyreserve.UI.SeatMap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.R

class SeatMapActivity : AppCompatActivity() {
    private lateinit var viewModel: SeatMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_map)

        viewModel = ViewModelProvider(this).get(SeatMapViewModel::class.java)

        // Initiate the API request using viewModel.fetchSeatMap(request)
        // Update UI based on response LiveData
    }
}