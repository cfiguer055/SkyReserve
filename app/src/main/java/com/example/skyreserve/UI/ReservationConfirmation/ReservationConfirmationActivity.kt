package com.example.skyreserve.UI.ReservationConfirmation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.Model.FlightInfo
import com.example.skyreserve.UI.CheckOut.CheckOutActivity
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityReservationConfirmationBinding

class ReservationConfirmationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReservationConfirmationBinding
    private lateinit var logger: UserInteractionLogger

    private var baggageCount = 1
    private var maxBaggageCount = 5

    private lateinit var departureCity: String
    private lateinit var arrivalCity: String
    private lateinit var departureTime: String
    private lateinit var arrivalTime: String
    private lateinit var departureAirport: String
    private lateinit var arrivalAirport: String
    private lateinit var flightDuration: String
    private lateinit var airline: String

    private var numPassengers : Int = 1
    private var basePrice: Double = 0.00
    private var seatChangeFee: Double = 0.00
    private var baggageFee: Double = 0.00
    private var tax: Double = 0.00
    private var reservationTotal: Double = 0.00

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //logger = (application as MyApp).logger
        //logger.logInteraction("ReservationConfirmationActivity:")

        loadIntentData()
        calculatePaymentSummary()

        //binding.headerTitle.text = "Review your trip to ${arrivalAirport}"

        val username = email.substringBefore("@")
        val passengerLabelTextView = binding.passengerLabelTextView
        passengerLabelTextView.text = "\t$username:"

        binding.departureChangeSeatsButton.setOnClickListener {
            //logger.logInteraction("Button clicked: ${binding.departureChangeSeatsButton.text}")
        }

        binding.departureDecrementBaggageButton.setOnClickListener {
            //logger.logInteraction("Button clicked: ${binding.departureDecrementBaggageButton.text}")
            if (baggageCount > 1) {
                baggageCount--
                binding.departureBaggageCountTextView.text = baggageCount.toString()
            }
            //logger.logInteraction("Passenger Count: $baggageCount")
        }

        binding.departureIncrementBaggageButton.setOnClickListener {
            //logger.logInteraction("Button clicked: ${binding.departureIncrementBaggageButton.text}")
            if (baggageCount < maxBaggageCount) {
                baggageCount++
                binding.departureBaggageCountTextView.text = baggageCount.toString()
            }
            //logger.logInteraction("Passenger Count: $baggageCount")
        }

        binding.editPartyButton.setOnClickListener {
            // logger.logInteraction("Button clicked: ${binding.editPartyButton.text}")
        }

        binding.confirmButton.setOnClickListener {
            //logger.logInteraction("Button clicked: ${binding.confirmButton.text}")
            navigateToCheckOut()
        }
    }


    private fun loadIntentData() {
        // Retrieve the data from the intent
        val flightInfo = intent.getSerializableExtra("EXTRA_FLIGHT_INFO") as? FlightInfo
        flightInfo?.let {
            // Assign the flightInfo data to variables
            departureCity = it.departureCity
            arrivalCity = it.arrivalCity
            departureTime = it.departureTime
            arrivalTime = it.arrivalTime
            departureAirport = it.departureAirport
            arrivalAirport = it.arrivalAirport
            flightDuration = it.flightDuration
            airline = it.airline
            basePrice = parsePrice(it.cost)
        }
        numPassengers = intent.getIntExtra("NUM_PASSENGERS", 1)
        email = intent.getStringExtra("EXTRA_EMAIL") ?: ""

        // Set the text of the TextViews with the assigned variables
        binding.departureCityTextView.text = "${departureCity} - ${arrivalCity}"
        binding.departureTimeTextView.text = "${departureTime} - ${arrivalTime}"
        binding.departureAirportTextView.text = "${departureAirport} - ${arrivalAirport}"
        binding.departureFlightDurationTextView.text = flightDuration
        binding.departureAirlineTextView.text = airline
    }

    private fun parsePrice(price: String): Double {
        // Remove non-numeric characters except the decimal point.
        Log.d("og price", price)
        val numericPrice = price.replace(Regex("[^\\d.]"), "")
        Log.d("numericPrice", numericPrice)
        Log.d("numericPrice", numericPrice.toDoubleOrNull().toString())
        return numericPrice.toDoubleOrNull() ?: 0.00 // Return 0.00 if the conversion fails
    }

    private fun calculatePaymentSummary() {
        tax = basePrice * 0.7
        reservationTotal = tax + basePrice

        binding.numberPassengersTextView.text = "Passengers: ${numPassengers}"
        binding.basePriceTextView.text = String.format("$%.2f\nper passenger", basePrice)
        binding.seatChangePriceTextView.text = String.format("$%.2f", seatChangeFee)
        binding.baggagePriceTextView.text = String.format("$%.2f", baggageFee)
        binding.taxTextView.text = String.format("$%.2f", tax)
        binding.totalTextView.text = String.format("$%.2f", reservationTotal)

    }

    private fun navigateToCheckOut() {
        //logger.logInteraction("Navigating To CheckOutActivity - TASK 2 Complete")

        val intent = Intent(this, CheckOutActivity::class.java)
        intent.putExtra("EXTRA_NUM_PASSENGERS", numPassengers)
        intent.putExtra("EXTRA_BASE_PRICE", basePrice)
        intent.putExtra("EXTRA_SEAT_CHANGE_FEE", seatChangeFee)
        intent.putExtra("EXTRA_BAGGAGE_FEE", baggageFee)
        intent.putExtra("EXTRA_TAX", tax)
        intent.putExtra("EXTRA_RESERVATION_TOTAL", reservationTotal)
        intent.putExtra("EXTRA_EMAIL", email)
        startActivity(intent)
    }

}