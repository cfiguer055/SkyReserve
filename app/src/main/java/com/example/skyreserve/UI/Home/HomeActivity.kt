package com.example.skyreserve.UI.Home

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.R
import com.example.skyreserve.Utility.AirportsData
import com.example.skyreserve.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting up the adapter for departAutoCompleteTextView
        val departAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, AirportsData.airports)
        binding.departAutoCompleteTextView.setAdapter(departAdapter)

        // Setting up the adapter for arriveAutoCompleteTextView
        val arriveAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, AirportsData.airports)
        binding.arriveAutoCompleteTextView.setAdapter(arriveAdapter)

        // Optional: you can customize the number of characters that will trigger the autocomplete suggestions
        binding.departAutoCompleteTextView.threshold = 1 //start searching from one character
        binding.arriveAutoCompleteTextView.threshold = 1

        binding.departureDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
                    binding.departureDateEditText.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.returnDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
                    binding.returnDateEditText.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

    }

    // Helper function to get month short name
    fun getMonthShortName(month: Int): String {
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return dateFormat.format(calendar.time)
    }
}