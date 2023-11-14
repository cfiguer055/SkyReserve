package com.example.skyreserve.UI.CheckOut

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.R
import com.example.skyreserve.UI.Success.SuccessActivity
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityCheckOutBinding
import java.io.File.separator

class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var logger: UserInteractionLogger

    private var numPassengers: Int = 1
    private var basePrice: Double = 0.00
    private var seatChangeFee: Double = 0.00
    private var baggageFee: Double = 0.00
    private var tax: Double = 0.00
    private var reservationTotal: Double = 0.00

    private lateinit var email: String

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logger = (application as MyApp).logger
        logger.logInteraction("CheckOutActivity:")

        // Retrieve intent extras
        loadIntentData()
        setRedAsterisk()

        // Add onFocusChangeListeners to each EditText
        val editTextList = listOf(
            binding.nameOnCardEditText,
            binding.cardNumberEditText,
            binding.expirationMonthEditText,
            binding.expirationYearEditText,
            binding.securityCodeEditText,
            binding.countryTerritoryEditText,
            binding.billingAddress1EditText,
            binding.cityEditText,
            binding.stateEditText,
            binding.zipCodeEditText
        )
        editTextList.forEach { editText ->
            editText.setOnFocusChangeListener { view, hasFocus ->
                val logMsg = if (hasFocus) {
                    "Focused on ${getResourceName(view.id)}"
                } else {
                    val editText = view as EditText
                    val textContent = editText.text.toString()
                    "Defocused from ${getResourceName(view.id)} with text: $textContent"
                }
                logger.logInteraction(logMsg)
            }
            editText.addTextChangedListener { resetErrorState() }
        }


//        binding.nameOnCardEditText.addTextChangedListener { resetErrorState() }
//        binding.cardNumberEditText.addTextChangedListener { resetErrorState() }
//        binding.expirationMonthEditText.addTextChangedListener { resetErrorState() }
//        binding.expirationYearEditText.addTextChangedListener { resetErrorState() }
//        binding.securityCodeEditText.addTextChangedListener { resetErrorState() }
//        binding.countryTerritoryEditText.addTextChangedListener { resetErrorState() }
//        binding.billingAddress1EditText.addTextChangedListener { resetErrorState() }
//        binding.cityEditText.addTextChangedListener { resetErrorState() }
//        binding.stateEditText.addTextChangedListener { resetErrorState() }
//        binding.zipCodeEditText.addTextChangedListener { resetErrorState() }

        binding.completePaymentButton.setOnClickListener {
            logger.logInteraction("Button clicked: ${binding.completePaymentButton.text}")
            if(areAllFieldsFilled()) {
                logger.logInteraction("Are All Fields Filled?: ${areAllFieldsFilled()}")
                navigateToSuccess()
            } else {
                logger.logInteraction("Are All Fields Filled?: ${areAllFieldsFilled()}")
                logger.logInteraction("Errors Displayed:")
                showError()
            }
        }
    }


    private fun navigateToSuccess() {
        logger.logInteraction("Navigating To SuccessActivity - TASK 3 Complete")

        val intent = Intent(this, SuccessActivity::class.java)
        // temp intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("EXTRA_EMAIL", email)
        startActivity(intent)
        // finish() temp // Call this if you want to close the current activity as well
    }

    private fun areAllFieldsFilled(): Boolean {
        val isNameOnCardSet = binding.nameOnCardEditText.text.toString().isNotEmpty()
        val cardNumberSet = binding.cardNumberEditText.text.toString().isNotEmpty()
        val isExpirationMonthSet = binding.expirationMonthEditText.text.toString().isNotEmpty()
        val isExpirationYearSet = binding.expirationYearEditText.text.toString().isNotEmpty()
        val isSecurityCodeSet = binding.securityCodeEditText.text.toString().isNotEmpty()

        val isCountryTerritorySet = binding.countryTerritoryEditText.text.toString().isNotEmpty()
        val isBillingAddressSet = binding.billingAddress1EditText.text.toString().isNotEmpty()
        val isCitySet = binding.cityEditText.text.toString().isNotEmpty()
        val isStateSet = binding.stateEditText.text.toString().isNotEmpty()
        val isZipCodeSet = binding.zipCodeEditText.text.toString().isNotEmpty()

        return isNameOnCardSet && cardNumberSet && isExpirationMonthSet && isExpirationYearSet &&
                isSecurityCodeSet && isCountryTerritorySet && isBillingAddressSet &&
                isCitySet && isStateSet && isZipCodeSet
    }

    private fun showError() {
        val paymentErrors = ArrayList<String>()
        val billingAddressErrors = ArrayList<String>()

        // Check each field and set an error if it's not filled
        if (binding.nameOnCardEditText.text.toString().isEmpty()) {
            binding.nameOnCardEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            paymentErrors.add("Enter a valid name.")
        }
        if (binding.cardNumberEditText.text.toString().isEmpty() || binding.cardNumberEditText.length() < 16) {
            binding.cardNumberEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            paymentErrors.add("Card Number must be 16 characters.")
        }
        if (binding.expirationMonthEditText.text.toString().isEmpty() || binding.expirationMonthEditText.length() < 2) {
            binding.expirationMonthEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            paymentErrors.add("Enter Valid Expiration Month.")
        }
        if (binding.expirationYearEditText.text.toString().isEmpty() || binding.expirationYearEditText.length() < 2) {
            binding.expirationYearEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            paymentErrors.add("Enter Valid Expiration Year.")
        }
        if (binding.securityCodeEditText.text.toString().isEmpty() || binding.expirationYearEditText.length() < 3) {
            binding.securityCodeEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            paymentErrors.add("Enter Valid Security Code.")
        }

        if (binding.countryTerritoryEditText.text.toString().isEmpty()) {
            binding.countryTerritoryEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            billingAddressErrors.add("Enter a Country/Territory.")
        }
        if (binding.billingAddress1EditText.text.toString().isEmpty()) {
            binding.billingAddress1EditText.setBackgroundResource(R.drawable.edit_text_error_background)
            billingAddressErrors.add("Enter Billing Address.")
        }
        if (binding.cityEditText.text.toString().isEmpty()) {
            binding.cityEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            billingAddressErrors.add("Enter City.")
        }
        if (binding.stateEditText.text.toString().isEmpty()) {
            binding.stateEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            billingAddressErrors.add("Enter State.")
        }
        if (binding.zipCodeEditText.length() < 3) {
            binding.zipCodeEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            billingAddressErrors.add("Enter Zip Code.")
        }

        // If any field is not filled, show the error message
        binding.errorTextView.text = getString(R.string.all_fields_required_error)
        binding.errorLayout.visibility = View.VISIBLE

        binding.errorPaymentTextView.text = paymentErrors.joinToString(separator = "\n")
        binding.errorBillingAddressTextView.text = billingAddressErrors.joinToString(separator = "\n")
        binding.errorPaymentTextView.visibility = View.VISIBLE
        binding.errorBillingAddressTextView.visibility = View.VISIBLE

        logger.logInteraction("Error Display is Visible")
        logger.logInteraction("Payment Errors: $paymentErrors")
        logger.logInteraction("Billing Address Errors: $billingAddressErrors")
    }

    private fun resetErrorState() {
        // Clear the error message
        binding.errorLayout.visibility = View.GONE

        // Reset the backgrounds to default
        binding.nameOnCardEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.cardNumberEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.expirationMonthEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.expirationYearEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.securityCodeEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.countryTerritoryEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.billingAddress1EditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.cityEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.stateEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.zipCodeEditText.setBackgroundResource(R.drawable.edit_text_background)

        //logger.logInteraction("Error State Reset")
    }

    private fun loadIntentData() {
        // Retrieve the data from the intent
        numPassengers = intent.getIntExtra("EXTRA_NUM_PASSENGERS", 1)
        basePrice = intent.getDoubleExtra("EXTRA_BASE_PRICE", 0.00)
        seatChangeFee = intent.getDoubleExtra("EXTRA_SEAT_CHANGE_FEE", 0.00)
        baggageFee = intent.getDoubleExtra("EXTRA_BAGGAGE_FEE", 0.00)
        tax = intent.getDoubleExtra("EXTRA_TAX", 0.00)
        reservationTotal = intent.getDoubleExtra("EXTRA_RESERVATION_TOTAL", 0.00)
        email = intent.getStringExtra("EXTRA_EMAIL") ?: ""

        logger.logInteraction("Passengers: ${numPassengers}")
        logger.logInteraction("Base Price: ${"%.2f".format(basePrice)}")
        logger.logInteraction("Seat Change Fee: ${"%.2f".format(seatChangeFee)}")
        logger.logInteraction("Baggage Fee: ${"%.2f".format(baggageFee)}")
        logger.logInteraction("Tax: ${"%.2f".format(tax)}")
        logger.logInteraction("Reservation Total: ${"%.2f".format(reservationTotal)}")

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

    // Utility function to get the resource name for logging purposes
    private fun getResourceName(resId: Int): String {
        return try {
            resources.getResourceEntryName(resId)
        } catch (e: Resources.NotFoundException) {
            "unknown"
        }
    }
}