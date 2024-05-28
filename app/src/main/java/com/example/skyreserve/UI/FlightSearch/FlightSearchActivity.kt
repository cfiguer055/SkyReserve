package com.example.skyreserve.UI.FlightSearch

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.Model.FlightAdapter
import com.example.skyreserve.Model.FlightInfo
import com.example.skyreserve.R
import com.example.skyreserve.UI.ReservationConfirmation.ReservationConfirmationActivity
import com.example.skyreserve.Util.AirportsData
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityFlightSearchBinding
import com.example.skyreserve.databinding.DialogAirportAutoCompleteBinding
import java.text.SimpleDateFormat
import java.util.*

class FlightSearchActivity : AppCompatActivity(), FlightAdapter.OnFlightClickListener {
    private lateinit var binding: ActivityFlightSearchBinding
    private lateinit var logger: UserInteractionLogger

    private lateinit var roundTrip : String
    private lateinit var departAirport : String
    private lateinit var arriveAirport : String
    private lateinit var departDate : String
//    private lateinit var returnDate : String
    private var numPassengers : Int = 1
    private var passengerCount = 1
    private val maxPassengerCount = 9

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //logger = (application as MyApp).logger
        logger.logInteraction("FlightSearchActivity:")

        loadIntentData()
        if(areAllFieldsFilled()) {
            logger.logInteraction("All Fields Are Filled?: ${areAllFieldsFilled()}")
            showFlightResults()
        } else {
            logger.logInteraction("All Fields Are Filled?: ${areAllFieldsFilled()}")
            logger.logInteraction("Flight Results Not Displayed Yet")
        }
        //Log.d("areAllFieldsFilled(): ", areAllFieldsFilled().toString())

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // When any RadioButton is checked, change the text color back to black
            resetErrorState()
            logger.logInteraction("Button clicked: ${binding.oneWayRadioButton.text}")
        }


        binding.departButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.departButton}")
            showAirportAutoCompleteDialog(true)
        }

        binding.arriveButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.arriveButton}")
            showAirportAutoCompleteDialog(false)
        }

        binding.departureDateEditText.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.departureDateEditText}")
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
                    binding.departureDateEditText.setText(selectedDate)
                    logger.logInteraction("Date Selected: ${binding.departureDateEditText.text}")
                    resetErrorState()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

//        binding.returnDateEditText.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            val datePickerDialog = DatePickerDialog(
//                this,
//                { _, year, month, dayOfMonth ->
//                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
//                    binding.returnDateEditText.setText(selectedDate)
//                    resetErrorState()
//                },
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)
//            )
//            datePickerDialog.show()
//        }

        binding.passengerCountTextView.text = passengerCount.toString()

        binding.decrementPassengerButton.setOnClickListener {
            logger.logInteraction("Button clicked: Decrement(${binding.decrementPassengerButton.text})")
            if (passengerCount > 1) {
                passengerCount--
                binding.passengerCountTextView.text = passengerCount.toString()
            }
            logger.logInteraction("Passenger Count: $passengerCount")
        }

        binding.incrementPassengerButton.setOnClickListener {
            logger.logInteraction("Button clicked: Increment(${binding.incrementPassengerButton.text})")
            if (passengerCount < maxPassengerCount) {
                passengerCount++
                binding.passengerCountTextView.text = passengerCount.toString()
            }
            logger.logInteraction("Passenger Count: $passengerCount")
        }

        binding.searchFlightButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.searchFlightButton.text}")
            if (areAllFieldsFilled()) {
                showFlightResults()
            } else {
                showError()
            }
        }
    }


    private fun areAllFieldsFilled(): Boolean {
        val isTripTypeSelected = binding.radioGroup.checkedRadioButtonId != -1
        val isDepartAirportSet = binding.departButton.text.toString().isNotEmpty()
        val isArriveAirportSet = binding.arriveButton.text.toString().isNotEmpty()
        val isDepartDateSet = binding.departureDateEditText.text.toString().isNotEmpty()
//        val isReturnDateSet = binding.returnDateEditText.text.toString().isNotEmpty()
        val isPassengerCountSet = binding.passengerCountTextView.text.toString()
            .isNotEmpty() // or check for a default value

        // Now, if all fields are set, adjust the layout accordingly
        return isDepartAirportSet && isArriveAirportSet && isDepartDateSet && isPassengerCountSet && isTripTypeSelected
    }

    private fun showError() {
        // Check each field and set an error if it's not filled
        if (binding.radioGroup.checkedRadioButtonId == -1) {
            binding.oneWayRadioButton.setTextColor(ContextCompat.getColor(this, R.color.red10))
            binding.roundTripRadioButton.setTextColor(ContextCompat.getColor(this, R.color.red10))
            logger.logInteraction("Search Error: ${binding.radioGroup.id}")
        }
        if (binding.departButton.text.toString().isEmpty()) {
            binding.departButton.setBackgroundResource(R.drawable.edit_text_error_background)
            logger.logInteraction("Search Error: ${binding.departButton}")
        }
        if (binding.arriveButton.text.toString().isEmpty()) {
            binding.arriveButton.setBackgroundResource(R.drawable.edit_text_error_background)
            logger.logInteraction("Search Error: ${binding.arriveButton}")
        }
        if (binding.departureDateEditText.text.toString().isEmpty()) {
            binding.departureDateEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            logger.logInteraction("Search Error: ${binding.departureDateEditText}")
        }
        if (binding.passengerCountTextView.text.toString().isEmpty()) {
            // Change background of passengers count to indicate error
        }
        // If any field is not filled, show the error message
        binding.errorTextView.text = getString(R.string.all_fields_required_error)
        binding.errorTextView.visibility = View.VISIBLE
        logger.logInteraction("Search Error Text View: ${binding.errorTextView.text}")
    }

    private fun resetErrorState() {
        // Clear the error message
        binding.errorTextView.text = ""
        binding.errorTextView.visibility = View.GONE

        // Reset the backgrounds and texts to default
        binding.oneWayRadioButton.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.roundTripRadioButton.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.departButton.setBackgroundResource(R.drawable.edit_text_background)
        binding.arriveButton.setBackgroundResource(R.drawable.edit_text_background)
        binding.departureDateEditText.setBackgroundResource(R.drawable.edit_text_background)
        // Add any other fields that need to be reset

        //logger.logInteraction("Error State Reset")
    }


    private fun showFlightResults() {
        // consider leaving header visible the entire time, just change the navigation for back button
        //binding.headerLayout.visibility = View.VISIBLE

        binding.searchFlight.visibility = View.GONE
        binding.flightResultsRecyclerView.visibility = View.VISIBLE

        val departParts = binding.departButton.text.toString().split(" - ")
        val departAirportCode = departParts.getOrNull(0) ?: "" // This will get "LAX" or an empty string if the part is not found.
        val departCity = departParts.getOrNull(1)?.split(",")?.getOrNull(0) ?: "" // This will get "LOS ANGELES" or an empty string if the part is not found.

        val arriveParts = binding.arriveButton.text.toString().split(" - ")
        val arriveAirportCode = arriveParts.getOrNull(0) ?: "" // This will get "LAX" or an empty string if the part is not found.
        val arriveCity = arriveParts.getOrNull(1)?.split(",")?.getOrNull(0) ?: ""

        binding.headerTitle.text = "$departAirportCode to $arriveAirportCode"
        logger.logInteraction("Flight Results Displayed for $departAirportCode to $arriveAirportCode")

        val flightList = listOf(
            FlightInfo("08:00", "10:00", departCity , arriveCity, departAirportCode, arriveAirportCode,"7h", "Spirit Airlines", "$500", roundTrip),
            FlightInfo("08:15", "14:45", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 30m", "Spirit Airlines", "$500", roundTrip),
            FlightInfo("09:30", "15:00", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 30m", "Delta", "$450", roundTrip),
            FlightInfo("10:45", "17:15", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 30m", "United", "$470", roundTrip),
            FlightInfo("11:20", "17:05", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "Southwest", "$530", roundTrip),
            FlightInfo("12:50", "19:35", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "American Airlines", "$550", roundTrip),
            FlightInfo("13:10", "18:55", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "JetBlue", "$520", roundTrip),
            FlightInfo("14:00", "20:15", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 15m", "Alaska Airlines", "$510", roundTrip),
            FlightInfo("15:35", "22:20", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "Frontier", "$435", roundTrip),
            FlightInfo("16:05", "22:50", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "Spirit", "$499", roundTrip),
            FlightInfo("17:40", "23:25", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "Delta", "$460", roundTrip),
            FlightInfo("18:25", "00:10", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "United", "$480", roundTrip),
            FlightInfo("19:15", "01:00", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "Southwest", "$540", roundTrip),
            FlightInfo("20:30", "03:15", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "American Airlines", "$560", roundTrip),
            FlightInfo("21:45", "04:30", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "JetBlue", "$525", roundTrip),
            FlightInfo("22:50", "05:35", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "Alaska Airlines", "$515", roundTrip)
        )

        // Initialize the RecyclerView and Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.flightResultsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = FlightAdapter(flightList, this) // Pass your flight data here
        recyclerView.adapter = adapter
    }

    private fun showFlightSearch() {
        binding.searchFlight.visibility = View.VISIBLE
        binding.flightResultsRecyclerView.visibility = View.GONE
    }

    private fun loadIntentData() {
        // Retrieve the data from the intent
        email = intent.getStringExtra("EXTRA_EMAIL") ?: ""

        roundTrip = intent.getStringExtra("ROUND_TRIP") ?: ""
        departAirport = intent.getStringExtra("DEPART_AIRPORT") ?: ""
        arriveAirport = intent.getStringExtra("ARRIVE_AIRPORT") ?: ""
        departDate = intent.getStringExtra("DEPART_DATE") ?: ""
//        returnDate = intent.getStringExtra("RETURN_DATE") ?: ""
        numPassengers = intent.getIntExtra("NUM_PASSENGERS", 1) // Assuming a default of 1 passenger

        // Update the UI with the retrieved data
        binding.radioGroup.check(getRadioButtonId(roundTrip))
        binding.departButton.text = departAirport
        binding.arriveButton.text = arriveAirport
        binding.departureDateEditText.setText(departDate)
//        binding.returnDateEditText.setText(returnDate)
        binding.passengerCountTextView.text = numPassengers.toString()
    }

    // Helper function to get the ID of the RadioButton based on the trip type
    private fun getRadioButtonId(tripType: String?): Int {
        return when (tripType) {
            "One way" -> R.id.oneWayRadioButton
            "Round trip" -> R.id.roundTripRadioButton
            else -> -1 // or any default or error handling you'd like
        }
    }

    override fun onBackPressed() {
        if(binding.searchFlight.isVisible) {
            super.onBackPressed()
        } else {
            showFlightSearch()
        }


//        val data = Intent()
//        // Put extras in the Intent
//        data.putExtra("ROUND_TRIP", binding.radioGroup.checkedRadioButtonId)
//        data.putExtra("DEPART_AIRPORT", binding.departButton.text.toString())
//        data.putExtra("ARRIVE_AIRPORT", binding.arriveButton.text.toString())
//        data.putExtra("DEPART_DATE", binding.departureDateEditText.text.toString())
////        data.putExtra("RETURN_DATE", binding.returnDateEditText.text.toString())
//        data.putExtra("NUM_PASSENGERS", binding.passengerCountTextView.text.toString())
//        // Other extras...
//
//        // Set the result with a result code and the Intent
//        setResult(Activity.RESULT_OK, data)
//
//        // Finish the current activity to go back to HomeActivity
//        // finish() temp
    }

    override fun onFlightClick(selectedFlightInfo: FlightInfo) {
        logger.logInteraction("Flight Selected: ${selectedFlightInfo.departureAirport} to ${selectedFlightInfo.arrivalAirport}" +
                "\tfrom${selectedFlightInfo.departureTime} to ${selectedFlightInfo.arrivalTime}")
        logger.logInteraction("Navigating to ReservationConfirmationActivity - TASK 1 Complete")

        // Handle the click, navigate to the confirmation page
        val intent = Intent(this, ReservationConfirmationActivity::class.java)
        intent.putExtra("EXTRA_FLIGHT_INFO", selectedFlightInfo) // Pass flight information
        intent.putExtra("NUM_PASSENGERS", numPassengers)
        intent.putExtra("EXTRA_EMAIL", email)
        startActivity(intent)
    }

    // Helper function to get month short name
    fun getMonthShortName(month: Int): String {
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return dateFormat.format(calendar.time)
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
                logger.logInteraction("Alert Dialog Cancelled")
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
                logger.logInteraction("Alert Dialog Button clicked: $airportCode - $city")

                dialog.dismiss()
                resetErrorState()
            }
            layout.addView(textView)
        }
    }}