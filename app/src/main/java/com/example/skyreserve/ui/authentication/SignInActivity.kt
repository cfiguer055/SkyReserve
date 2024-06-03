package com.example.skyreserve.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.skyreserve.R
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.ui.home.HomeActivity
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.Util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
* If you annotate an Android class with @AndroidEntryPoint, then you also must annotate
* Android classes that depend on it (Inject). For example, if you annotate a fragment, then you must
*  also annotate any activities where you use that fragment.*/
@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var sessionManager: LocalSessionManager

    @Inject
    lateinit var logger: UserInteractionLogger


    private lateinit var binding: ActivitySignInBinding

    private val authViewModel: AuthViewModel by viewModels()
    // private lateinit var userViewModel: UserViewModel

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val userAccountDao = (application as MyApp).userAccountDao
        //authRepository = AuthRepository(userAccountDao)
        // val sessionManager = LocalSessionManager(this)

        // userViewModel = ViewModelProvider(this, UserViewModelFactory(authRepository, sessionManager, this))[UserViewModel::class.java]
        // signInViewModel = ViewModelProvider(this, SignInViewModelFactory()).get(SignInViewModel::class.java)

        //logger = (application as MyApp).logger


        // Add TextChangedListeners to reset the colors when the user starts typing
        binding.emailEditText.addTextChangedListener { resetInputUI() }
        binding.passwordEditText.addTextChangedListener { resetInputUI() }

        binding.signInButton.setOnClickListener {
            email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            try {
                disableSignInUI()

//                signInViewModel.signIn(email, password) { result ->
//                    when (result) {
//                        SignInResult.SUCCESS -> navigateToHome()
//                        SignInResult.INVALID_CREDENTIALS -> showSignInError("Invalid credentials. Please try again.")
//                        SignInResult.NETWORK_ERROR -> showSignInError("Network error. Please check your internet connection.")
//                        SignInResult.UNKNOWN_ERROR -> showSignInError("An unknown error occurred.")
//                    }
//                }
                authViewModel.signIn(email, password)
                observeSignInResult()

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
            navigateToSignUp()
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
//        logger.init(email)
//        logger.logInteraction("Log Start: $email")
//        logger.logInteraction("Navigating to HomeActivity")

        val intent = Intent(this, HomeActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("EXTRA_EMAIL", email)
        startActivity(intent)
        // finish() temp
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

        startActivity(intent)
    }

    private fun navigateToForgotPassword() {

    }

    private fun observeSignInResult() {
        authViewModel.signInResult.observe(this) { result ->
            when (result) {
                SignInResult.SUCCESS -> navigateToHome()
                SignInResult.INVALID_CREDENTIALS -> showSignInError("Invalid credentials. Please try again.")
                SignInResult.NETWORK_ERROR -> showSignInError("Network error. Please check your internet connection.")
                SignInResult.UNKNOWN_ERROR -> showSignInError("An unknown error occurred.")
            }
        }
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
