package com.example.skyreserve.UI.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.R
import com.example.skyreserve.UI.Home.HomeActivity
import com.example.skyreserve.UI.SignUp.SignUpActivity
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.databinding.ActivitySignInBinding


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var signInViewModel: SignInViewModel

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInViewModel = ViewModelProvider(this, SignInViewModelFactory()).get(SignInViewModel::class.java)

        // Add TextChangedListeners to reset the colors when the user starts typing
        binding.emailEditText.addTextChangedListener { resetInputUI() }
        binding.passwordEditText.addTextChangedListener { resetInputUI() }

        binding.signInButton.setOnClickListener {
            email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            try {
                disableSignInUI()

                signInViewModel.signIn(email, password) { result ->
                    enableSignInUI() // Re-enable the UI elements

                    when (result) {
                        SignInResult.SUCCESS -> navigateToHome()
                        SignInResult.INVALID_CREDENTIALS -> showSignInError("Invalid credentials. Please try again.")
                        SignInResult.NETWORK_ERROR -> showSignInError("Network error. Please check your internet connection.")
                        SignInResult.UNKNOWN_ERROR -> showSignInError("An unknown error occurred.")
                    }
                }
            } catch (e: Exception) {
                Log.e("SignInActivity", "Error during sign in", e)
            } finally {
                enableSignInUI()
            }
        }

        binding.signInWithGoogle.setOnClickListener {
            email = "google@gmail.com"
            navigateToHome()
        }

        // CHANGE TEXTVIEW
        binding.signUpLinkTextView.setOnClickListener {
            navigateToSingUp()
        }
    }

    private fun resetInputUI() {
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""

        // Reset the EditText and TextView to their default colors
        binding.emailEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_background)
    }

    private fun disableSignInUI() {
        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
        binding.signInButton.isEnabled = false
    }

    private fun enableSignInUI() {
        binding.emailEditText.isEnabled = true
        binding.passwordEditText.isEnabled = true
        binding.signInButton.isEnabled = true
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        // temp intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("EXTRA_EMAIL", email)
        startActivity(intent)
        // finish() temp
    }

    private fun navigateToSingUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        // temp intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        // finish() temp
    }

    private fun navigateToForgotPassword() {
    }

    private fun showSignInError(message: String) {
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = message

        // Change the EditText background to indicate error
        binding.emailEditText.setBackgroundResource(R.drawable.edit_text_error_background)
        binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)

        // Show the error message to the user, e.g., using a Toast or a Snackbar
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
