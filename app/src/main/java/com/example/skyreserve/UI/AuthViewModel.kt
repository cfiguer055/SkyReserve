package com.example.skyreserve.UI

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.UI.Home.UserAccountViewModel
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.Util.SignUpResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import androidx.activity.viewModels
import androidx.core.util.PatternsCompat


/*
* The parameters of an annotated constructor of a class are the dependencies of that class.
* In the example, UserViewModel has AuthRepository, LocalSessionManager, and Context as a
* dependency. Therefore, Hilt must also know how to provide instances of AuthRepository,
* LocalSessionManager, and Context.
* */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository, // Handles authentication logic
    private val sessionManager: LocalSessionManager, // Manages local session state
    @ApplicationContext private val context: Context, // Added for network check
) : ViewModel() {


    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    // LiveData or StateFlow to hold the sign-up and sign-in result
    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> get() = _signUpResult

    private val _signInResult = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> get() = _signInResult

    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser: StateFlow<UserAccount?> = _currentUser


    // Function to attempt user login
    fun signIn(emailAddress: String, password: String) {
        viewModelScope.launch {

            val signInSuccess = authRepository.signIn(emailAddress, password)
            val isEmailExisting = authRepository.isEmailExisting(emailAddress)
            when {
                !signInSuccess && isEmailExisting -> {
                    _signInResult.value = SignInResult.INVALID_CREDENTIALS
                }
                !isNetworkAvailable() -> {
                    // Simulate Network even though Room db is local
                    _signInResult.value = SignInResult.NETWORK_ERROR
                }
                else -> {
                    if(signInSuccess) {
                        _signInResult.value = SignInResult.SUCCESS

                        val userAccount = authRepository.getUserAccountByEmailAddress(emailAddress)
                        _isLoggedIn.value = true
                        _currentUser.value = userAccount
                        userAccount?.let {
                            // Generate the token (ideally, this would be done by a secure server)
                            val token = UUID.randomUUID().toString()
                            // Create the login session with the generated token
                            sessionManager.createLoginSession(it.emailAddress, token)
                        }


                        _isLoggedIn.value = true
                        _currentUser.value = userAccount
                    } else {
                        _signInResult.value = SignInResult.UNKNOWN_ERROR

                        // Handle login failure, update UI accordingly
                        _isLoggedIn.value = false
                        _currentUser.value = null
                    }
                }
            }
        }
    }

    // Function to attempt user sign-up
    fun signUp(emailAddress: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            when {
                emailAddress.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                    _signUpResult.value = SignUpResult.MISSING_INFORMATION
                }
                !isValidEmail(emailAddress) -> {
                    _signUpResult.value = SignUpResult.INVALID_EMAIL
                }
                isEmailExisting(emailAddress) -> {
                    _signUpResult.value = SignUpResult.EXISTING_EMAIL
                }
                password.length < 8 -> {
                    _signUpResult.value = SignUpResult.SHORT_PASSWORD
                }
                !password.matches(Regex(".*[A-Z].*")) -> {
                    _signUpResult.value = SignUpResult.MISSING_CAPITAL_LETTER
                }
                !password.matches(Regex(".*[a-z].*")) -> {
                    _signUpResult.value = SignUpResult.MISSING_LOWCASE_LETTER
                }
                !password.matches(Regex(".*\\d.*")) -> {
                    _signUpResult.value = SignUpResult.MISSING_DIGIT
                }
                password != confirmPassword -> {
                    _signUpResult.value = SignUpResult.PASSWORD_NO_MATCH
                }
                !isNetworkAvailable() -> {
                    _signUpResult.value = SignUpResult.NETWORK_ERROR
                }
                else -> {
                    val success = authRepository.signUp(emailAddress, password)
                    if (success) {
                        _signUpResult.value = SignUpResult.SUCCESS

                        // Create login session after successful sign up
                        val token = UUID.randomUUID().toString()
                        sessionManager.createLoginSession(emailAddress, token)

                        // Set the current user
                        val userAccount = UserAccount(emailAddress = emailAddress, password = password) // Replace with actual user account details
                        _isLoggedIn.value = true
                        _currentUser.value = userAccount
                    } else {
                        _signUpResult.value = SignUpResult.UNKNOWN_ERROR

                        // Handle sign up failure, update UI accordingly
                        _isLoggedIn.value = false
                        _currentUser.value = null
                    }
                }
            }
        }
    }

    fun validateSession() : Boolean {
        return sessionManager.isTokenValid()
    }

    fun refreshSessionToken() {
        sessionManager.renewToken()
    }


    // Function to log out the user
    fun logout() {
        sessionManager.logoutUser()
        _isLoggedIn.value = false
        _currentUser.value = null
    }



    // Call this when the app starts or resumes to check if the user should remain logged in
//    fun validateSession() : Boolean {
//        return if (!sessionManager.isTokenValid()) {
//            logout()
//            false
//        } else {
//            true
//        }
//    }


    // Call this method to renew the token
//    fun refreshSessionToken() {
//        sessionManager.renewToken()
//    }


    // Network availability check function
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Email existence check function
    private suspend fun isEmailExisting(email: String): Boolean {
        return authRepository.isEmailExisting(email)
    }
    // Additional ViewModel logic...

    // Function to validate email
    private fun isValidEmail(email: String): Boolean {
        /*
        28-05-2024
        If you're creating this as a standard unit test (in /test), then JUnit does not use the Android
         framework. Any Android-specific methods are stubbed and will fail to run.
        You'd either need to define Patterns.EMAIL_ADDRESS yourself from scratch (or use "PatternsCompat.EMAIL_ADRESS.matcher()") or run this test as an
        Instrumentation test in /androidTest. Alternatively, take a look at using Robolectric as it provides an
        implementation of the Android SDK for use in local JUnit tests.
         */
        // return android.util.PatternsCompat.EMAIL_ADRESS.matcher(email).matches()

        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }


//    private fun isValidPassword(password: String, confirmPassword: String): Boolean {
//        return when {
//            password.length < 8 ||
//                    !password.matches(Regex(".*[A-Z].*")) || // Missing capital letter
//                    !password.matches(Regex(".*[a-z].*")) || // Missing lowercase letter
//                    !password.matches(Regex(".*\\d.*")) ||   // Missing digit
//                    password != confirmPassword -> {                // Passwords don't match
//                false
//            } else -> {
//                true // Valid Password
//            }
//        }
//    }
}

