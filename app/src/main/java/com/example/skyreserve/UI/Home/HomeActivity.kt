package com.example.skyreserve.UI.Home

import LocalSessionManager
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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.R
import com.example.skyreserve.UI.Account.AccountActivity
import com.example.skyreserve.UI.FlightSearch.FlightSearchActivity
import com.example.skyreserve.Util.AirportsData
import com.example.skyreserve.Util.UserData
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityHomeBinding
import com.example.skyreserve.databinding.DialogAirportAutoCompleteBinding
import com.example.skyreserve.databinding.DialogSignUpBinding
import java.text.SimpleDateFormat
import com.example.skyreserve.Repository.DatabaseRepository.UserAccountRepository
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel

    // This is the request code you will use when launching the FlightSearchActivity
    private val FLIGHT_SEARCH_REQUEST_CODE = 1  // This can be any integer unique to the Activity
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sessionManager: LocalSessionManager
    private lateinit var userAccountRepository: UserAccountRepository
    private lateinit var logger: UserInteractionLogger

    private var passengerCount = 1
    private val maxPassengerCount = 9

    private lateinit var email: String

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

        // Initialize logger
        logger = (applicationContext as MyApp).logger
        // Initialize sessionManager
        sessionManager = (applicationContext as MyApp).sessionManager

        val userAccountDao = (application as MyApp).userAccountDao
        userAccountRepository = UserAccountRepository(application)
        val sessionManager = LocalSessionManager(this)

        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(userAccountRepository, sessionManager, this))[HomeViewModel::class.java]


        val isNewUser = intent.getBooleanExtra("FROM_SIGN_UP", false)
        if (isNewUser) {
            showSignUpDialog()
        } else {
            fetchUserDetailsAfterSignUp()
        }

        // Validate session and get user email
        if (sessionManager.isTokenValid()) {
            // Use userEmail to fetch user details if needed

            email = sessionManager.getUserEmail().toString()
            homeViewModel.fetchUserDetails(email)

            homeViewModel.userName.observe(this) { name ->
                binding.nameText.text = name
            }

        } else {
            // Handle invalid session, e.g., navigate to login
            binding.nameText.text = ""
        }

        setupBottomNavigation()

        logger = (application as MyApp).logger
        logger.logInteraction("HomeActivity:")

        binding.oneWayRadioButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.oneWayRadioButton.text}")
        }

        binding.departButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.departButton.id}")
            showAirportAutoCompleteDialog(true)
        }

        binding.arriveButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.arriveButton.id}")
            showAirportAutoCompleteDialog(false)
        }

        binding.departureDateEditText.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.departureDateEditText.id}")
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
                    binding.departureDateEditText.setText(selectedDate)
                    logger.logInteraction("Date Selected: ${binding.departureDateEditText.text}")
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
                    logger.logInteraction("Button clicked: ${binding.returnDateEditText.text}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

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
            navigateToFlightSearch()
        }

    }

    private fun navigateToFlightSearch() {
        val intent = Intent(this, FlightSearchActivity::class.java)
        // DONT UNCOMMENT intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        val tripType = when (selectedRadioButtonId) {
            R.id.oneWayRadioButton -> "One way"
            R.id.roundTripRadioButton -> "Round trip"
            else -> "Not selected"
        }
        logger.logInteraction("Flight Search Fields")
        logger.logInteraction("DEPART_AIRPORT : ${binding.departButton.text}")
        logger.logInteraction("ARRIVE_AIRPORT : ${binding.arriveButton.text}")
        logger.logInteraction("DEPART_DATE : ${binding.departureDateEditText.text}")
        logger.logInteraction("ROUND_TRIP : $tripType")
        logger.logInteraction("NUM_PASSENGERS : $passengerCount")
        logger.logInteraction("Navigating To FlightSearchActivity")

        intent.putExtra("EXTRA_EMAIL", email)
        intent.putExtra("ROUND_TRIP", tripType)
        intent.putExtra("DEPART_AIRPORT", binding.departButton.text.toString())
        intent.putExtra("ARRIVE_AIRPORT", binding.arriveButton.text.toString())
        intent.putExtra("DEPART_DATE", binding.departureDateEditText.text.toString())
        //intent.putExtra("RETURN_DATE", binding.returnDateEditText.text.toString())
        intent.putExtra("NUM_PASSENGERS", passengerCount)

        startActivity(intent)
    }

    private fun navigateToAccount() {
        val intent = Intent(this, AccountActivity::class.java)
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
        val binding = DialogSignUpBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("Complete Your Profile")
            setView(binding.root)
            setPositiveButton("Submit") { _, _ ->
                // Get the user's email from session manager
                val userEmail = LocalSessionManager(this@HomeActivity).getUserEmail() ?: return@setPositiveButton

                // Collect data from the UI
                val userData = UserData(
                    firstName = binding.firstNameEditText.text.toString(),
                    lastName = binding.lastNameEditText.text.toString(),
                    gender = binding.genderEditText.text.toString(),
                    phone = binding.phoneNumberEditText.text.toString(),
                    dateOfBirth = binding.dateOfBirthEditText.text.toString(),
                    address = binding.addressEditText.text.toString(),
                    stateCode = binding.stateCodeEditText.text.toString(),
                    countryCode = binding.countryCodeEditText.text.toString(),
                    passport = binding.passportEditText.text.toString()
                )
                // Pass data to ViewModel to handle the update
                homeViewModel.updateUserDetails(userData)

                // After updating, fetch user details to update UI
                fetchUserDetailsAfterSignUp()
            }
            setNegativeButton("Cancel", null)
        }.create()
        dialog.show()
    }

    private fun fetchUserDetailsAfterSignUp() {
        if (sessionManager.isTokenValid()) {
            email = sessionManager.getUserEmail().toString()
            homeViewModel.fetchUserDetails(email)

            Log.d("fetchUserDetailsAfterSignUp", "Valid Email")

            homeViewModel.userName.observe(this) { name ->
                Log.d("fetchUserDetailsAfterSignUp", name.toString())
                binding.nameText.text = name
            }
        } else {
            // Handle invalid session
            binding.nameText.text = ""
            Log.d("fetchUserDetailsAfterSignUp", "Invalid Email")
        }
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
                logger.logInteraction("Alert Dialog Button clicked: $airportCode - $city")

                dialog.dismiss()
            }
            layout.addView(textView)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home action
                    true
                }
                R.id.navigation_order -> {
                    // Handle order action
                    true
                }
                R.id.navigation_search -> {
                    // Handle search action
                    navigateToFlightSearch()
                    true
                }
                R.id.navigation_account -> {
                    // Handle account action
                    navigateToAccount()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        // Create an intent that mimics the Home button being pressed
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

}