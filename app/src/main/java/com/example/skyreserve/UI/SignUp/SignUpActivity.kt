package com.example.skyreserve.UI.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.App.MyApp
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.UI.Home.HomeActivityOld
import com.example.skyreserve.UI.SignIn.SignInActivity
import com.example.skyreserve.Util.ActivityState
import com.example.skyreserve.Util.SignUpResult
import com.example.skyreserve.databinding.ActivitySignUpBinding
import kotlinx.coroutines.launch


/*
The activity manages the SignUpFragment.
It sets up the navigation flow for the sign-up process.
You can include any additional logic for initializing the app here.
*/
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAccountDao = (application as MyApp).userAccountDao
        authRepository = AuthRepository(userAccountDao) // Create an instance of AuthRepository with the required dependencies
        signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory(authRepository, this))[SignUpViewModel::class.java]


        binding.emailEditText.addTextChangedListener { resetInputUI() }
        binding.passwordEditText.addTextChangedListener { resetInputUI() }
        binding.confirmPasswordEditText.addTextChangedListener { resetInputUI() }

        binding.passwordEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // User has focused on the password field
                displayPasswordRequirements()
            } else {
                // User has defocused the password field
                hidePasswordRequirements()
            }
        }

        binding.signUpButton.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            try {
                disableSignUpUI()

                signUpViewModel.viewModelScope.launch {
                    signUpViewModel.signUp(email, password, confirmPassword) { result ->
                        enableSignUpUI()

                        when (result) {
                            SignUpResult.SUCCESS -> navigateToHome()
                            SignUpResult.MISSING_INFORMATION -> showSignUpError("Please fill out all fields.", "MISSING_INFORMATION")
                            SignUpResult.INVALID_EMAIL -> showSignUpError("Invalid email format.", "INVALID_EMAIL")
                            SignUpResult.EXISTING_EMAIL -> showSignUpError("This email is already taken. Try another.", "INVALID_EMAIL")
                            SignUpResult.SHORT_PASSWORD -> showSignUpError("Your password is too short. It needs to be at least 8 characters.", "INVALID_PASSWORD")
                            SignUpResult.MISSING_CAPITAL_LETTER -> showSignUpError("Add at least one capital letter in your password.", "INVALID_PASSWORD")
                            SignUpResult.MISSING_LOWCASE_LETTER -> showSignUpError("Add at least one lowercase letter in your password.", "INVALID_PASSWORD")
                            SignUpResult.MISSING_DIGIT -> showSignUpError("Include at least one number in your password.", "INVALID_PASSWORD")
                            SignUpResult.PASSWORD_NO_MATCH -> showSignUpError("Oops! Your passwords do not match.", "PASSWORD_NO_MATCH")
                            SignUpResult.TERMS_NOT_ACCEPTED -> showSignUpError("You must accept the terms and conditions to continue.", "TERMS_NOT_ACCEPTED")
                            SignUpResult.NETWORK_ERROR -> showSignUpError("Check your internet connection and try again.", "NETWORK_ERROR")
                            SignUpResult.UNKNOWN_ERROR -> showSignUpError("Something went wrong. Please try again later.", "UNKNOWN_ERROR")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("SignUpActivity", "Error during sign up", e)
            } finally {
                enableSignUpUI()
            }
        }

        binding.signInLinkTextView.setOnClickListener {
            navigateToSignIn()
        }
    }


    private fun resetInputUI() {
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""

        // Reset the EditText and TextView to their default colors
        binding.emailEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_background)
    }

    private fun enableSignUpUI() {
        binding.emailEditText.isEnabled = true
        binding.passwordEditText.isEnabled = true
        binding.confirmPasswordEditText.isEnabled = true
        binding.signUpButton.isEnabled = true
    }

    private fun disableSignUpUI() {
        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
        binding.confirmPasswordEditText.isEnabled = false
        binding.signUpButton.isEnabled = false
    }

    fun displayPasswordRequirements() {
        binding.passwordRequirementsTextView.visibility = View.VISIBLE
    }

    fun hidePasswordRequirements() {
        binding.passwordRequirementsTextView.visibility = View.GONE
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivityOld::class.java)
        // temp intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("FROM_SIGN_UP", true)
        startActivity(intent)
        // finish() temp
    }

    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

        startActivity(intent)
    }

    private fun showSignUpError(message: String, errorCode: String) {
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = message

        when (errorCode) {
            "MISSING_INFORMATION" -> {
                // Check each EditText and if empty, change the background
                if (binding.emailEditText.text.isNullOrEmpty()) {
                    binding.emailEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                }
                if (binding.passwordEditText.text.isNullOrEmpty()) {
                    binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                }
                if (binding.confirmPasswordEditText.text.isNullOrEmpty()) {
                    binding.confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                }
            }
            "INVALID_EMAIL" -> {
                // Handle INVALID_EMAIL error
                binding.emailEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            "INVALID_PASSWORD" -> {
                // This case can handle multiple password-related errors
                // Handle INVALID_PASSWORD error
                binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            "PASSWORD_NO_MATCH" -> {
                // Handle PASSWORD_NO_MATCH error
                binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                binding.confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            "TERMS_NOT_ACCEPTED" -> {
                // Handle TERMS_NOT_ACCEPTED error
            }
            "NETWORK_ERROR" -> {
                // Handle NETWORK_ERROR error
            }
            else -> {
                // Optionally, handle any other unexpected error codes
            }
        }
    }
}
