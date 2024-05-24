

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.Util.SignUpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class UserViewModel(
    private val authRepository: AuthRepository, // Handles authentication logic
    private val sessionManager: LocalSessionManager, // Manages local session state
    private val context: Context // Added for network check
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

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
                !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> {
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

    // Function to log out the user
    fun logout() {
        sessionManager.logoutUser()
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    // Call this when the app starts or resumes to check if the user should remain logged in
    fun validateSession() {
        if (!sessionManager.isTokenValid()) {
            logout()
        }
    }

    // Call this method to renew the token
    fun refreshSessionToken() {
        sessionManager.renewToken()
    }


    // Network availability check function
    private fun isNetworkAvailable(): Boolean {
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
}

