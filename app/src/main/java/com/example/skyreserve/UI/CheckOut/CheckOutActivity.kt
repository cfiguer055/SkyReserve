package com.example.skyreserve.UI.CheckOut

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.UI.Success.SuccessActivity
import com.example.skyreserve.databinding.ActivityCheckOutBinding

class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutBinding

    private var numPassengers: Int = 1
    private var basePrice: Double = 0.00
    private var seatChangeFee: Double = 0.00
    private var baggageFee: Double = 0.00
    private var tax: Double = 0.00
    private var reservationTotal: Double = 0.00

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve intent extras
        loadIntentData()

        binding.completePaymentButton.setOnClickListener {
            navigateToSuccess()
        }
    }

    private fun navigateToSuccess() {
        val intent = Intent(this, SuccessActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Call this if you want to close the current activity as well
    }

    private fun loadIntentData() {
        // Retrieve the data from the intent
        numPassengers = intent.getIntExtra("EXTRA_NUM_PASSENGERS", 1)
        basePrice = intent.getDoubleExtra("EXTRA_BASE_PRICE", 0.00)
        seatChangeFee = intent.getDoubleExtra("EXTRA_SEAT_CHANGE_FEE", 0.00)
        baggageFee = intent.getDoubleExtra("EXTRA_BAGGAGE_FEE", 0.00)
        tax = intent.getDoubleExtra("EXTRA_TAX", 0.00)
        reservationTotal = intent.getDoubleExtra("EXTRA_RESERVATION_TOTAL", 0.00)

        binding.numberPassengersTextView.text = "Passengers: ${numPassengers}"
        binding.basePriceTextView.text = String.format("$%.2f\nper passenger", basePrice)
        binding.seatChangePriceTextView.text = String.format("$%.2f", seatChangeFee)
        binding.baggagePriceTextView.text = String.format("$%.2f", baggageFee)
        binding.taxTextView.text = String.format("$%.2f", tax)
        binding.totalTextView.text = String.format("$%.2f", reservationTotal)
    }

    private fun setRedAsterisk() {
        setRedAsteriskForTextView(binding.nameOnCardLabel, "Name on Card *")
        setRedAsteriskForTextView(binding.cardNumberLabel, "Card Number *")
        setRedAsteriskForTextView(binding.expirationDateLabel, "Expiration Date *")
        setRedAsteriskForTextView(binding.securityCodeLabel, "Security Code *")

        setRedAsteriskForTextView(binding.countryTerritoryLabel, "Country/Territory *")
        setRedAsteriskForTextView(binding.billingAddress1Label, "Billing Address 1 *")
        setRedAsteriskForTextView(binding.cityLabel, "City *")
        setRedAsteriskForTextView(binding.stateLabel, "State/Province/Region *")
        setRedAsteriskForTextView(binding.zipCodeLabel, "Zip/Postal Code *")
    }

    private fun setRedAsteriskForTextView(textView: TextView, labelText: String) {
        val s = SpannableString(labelText)
        val redSpan = ForegroundColorSpan(Color.RED)
        s.setSpan(redSpan, s.length - 1, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = s
    }
}