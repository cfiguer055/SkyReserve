package com.example.skyreserve.UI.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.R
import com.example.skyreserve.UI.FlightSearch.FlightSearchActivity
import com.example.skyreserve.Utility.AirportsData
import com.example.skyreserve.databinding.ActivityHomeBinding
import com.example.skyreserve.databinding.DialogAirportAutoCompleteBinding
import com.example.skyreserve.databinding.DialogSignUpBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    // This is the request code you will use when launching the FlightSearchActivity
    private val FLIGHT_SEARCH_REQUEST_CODE = 1  // This can be any integer unique to the Activity
    private lateinit var binding: ActivityHomeBinding
    private var passengerCount = 1
    private val maxPassengerCount = 9

    // Initialize the ActivityResultLauncher
    private val startFlightSearchActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the data from FlightSearchActivity
            val data = result.data
            // Use data to get the extras and do something with them
            val tripType = data?.getStringExtra("ROUND_TRIP")
            val departAirport = data?.getStringExtra("DEPART_AIRPORT")
            // ... and so on with other extras
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isNewUser = intent.getBooleanExtra("FROM_SIGN_UP", false)
        if (isNewUser) {
            showSignUpDialog()
        }

        // not sure if i need this?
        //binding.oneWayRadioButton.setOnClickListener {  }

        binding.departButton.setOnClickListener {
            showAirportAutoCompleteDialog(true)
        }

        binding.arriveButton.setOnClickListener {
            showAirportAutoCompleteDialog(false)
        }

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

        binding.passengerCountTextView.text = passengerCount.toString()

        binding.decrementPassengerButton.setOnClickListener {
            if (passengerCount > 1) {
                passengerCount--
                binding.passengerCountTextView.text = passengerCount.toString()
            }
        }

        binding.incrementPassengerButton.setOnClickListener {
            if (passengerCount < maxPassengerCount) {
                passengerCount++
                binding.passengerCountTextView.text = passengerCount.toString()
            }
        }

        binding.searchFlightButton.setOnClickListener {
            navigateToFlightSearch()
        }

    }

    private fun navigateToFlightSearch() {
        val intent = Intent(this, FlightSearchActivity::class.java)
        // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        val tripType = when (selectedRadioButtonId) {
            R.id.oneWayRadioButton -> "One way"
            R.id.roundTripRadioButton -> "Round trip"
            else -> "Not selected"
        }
        intent.putExtra("ROUND_TRIP", tripType)
        intent.putExtra("DEPART_AIRPORT", binding.departButton.text.toString())
        intent.putExtra("ARRIVE_AIRPORT", binding.arriveButton.text.toString())
        intent.putExtra("DEPART_DATE", binding.departureDateEditText.text.toString())
        intent.putExtra("RETURN_DATE", binding.returnDateEditText.text.toString())
        intent.putExtra("NUM_PASSENGERS", passengerCount)

        startActivity(intent)
    }



    // Helper function to get month short name
    fun getMonthShortName(month: Int): String {
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return dateFormat.format(calendar.time)
    }

    private fun showSignUpDialog() {
        // Inflate the layout using view binding
        val binding = DialogSignUpBinding.inflate(layoutInflater)

        // Create the AlertDialog
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("Complete Your Profile")
            setView(binding.root)
            setPositiveButton("Submit") { dialog, which ->
                // Here you would handle the submission of the data
                val firstName = binding.firstName.text.toString()
                val lastName = binding.lastName.text.toString()
                // ... get the data from other fields like gender and dob
                // Now you can use the data to update the user's profile
            }
            setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
        }.create()

        // Show the AlertDialog
        dialog.show()
    }

    private fun showAirportAutoCompleteDialog(departure: Boolean) {

        // Inflate the layout for the dialog
        val binding = DialogAirportAutoCompleteBinding.inflate(layoutInflater)
        val searchEditText = binding.searchEditText
        val airportsListLayout = binding.airportsListLayout

        // Create the AlertDialog and set its view to our inflated layout
        val dialog = AlertDialog.Builder(this).apply {
            if (departure) setTitle("Departure Airport") else setTitle("Arrival Airport")
            searchEditText.hint = if (departure) "Enter departure airport" else "Enter arrival airport"
            setView(binding.root) // This sets the custom view for the dialog
            setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
        }.create()

        // Show the AlertDialog
        dialog.show()

        // Set up a text changed listener on the search EditText to filter the list as the user types
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val filteredAirports = filterAirports(s.toString(), AirportsData.airports)
                updateAirportsList(departure, dialog, airportsListLayout, filteredAirports)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    // Filter airports based on the search query
    fun filterAirports(query: String, airports: Array<String>): List<String> {
        return airports.filter {
            it.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        }
    }

    // Update the list of airports displayed in the ScrollView
    @SuppressLint("SetTextI18n")
    fun updateAirportsList(departure: Boolean, dialog: AlertDialog, layout: LinearLayout, airports: List<String>) {
        // Remove all views before adding the new filtered list
        layout.removeAllViews()

        // Add TextViews for each filtered airport
        for (airport in airports) {
            val textView = TextView(this)
            textView.text = airport
            textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textView.setPadding(16, 16, 16, 16) // for example
            textView.setOnClickListener {
                // Handle the airport selection here
                val parts = airport.split(" - ")
                val airportCode = parts.getOrNull(1) ?: "" // This will get "LAX" or an empty string if the part is not found.
                val city = parts.getOrNull(2)?.split(",")?.getOrNull(0) ?: "" // This will get "LOS ANGELES" or an empty string if the part is not found.
                if(departure) binding.departButton.text = "$airportCode - $city" else binding.arriveButton.text = "$airportCode - $city"

                dialog.dismiss()
            }
            layout.addView(textView)
        }
    }
}