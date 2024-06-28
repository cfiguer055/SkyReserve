package com.example.skyreserve.ui.authentication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.util.LocalSessionManager
import com.example.skyreserve.util.SignInResult
import com.example.skyreserve.util.SignUpResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import androidx.core.util.PatternsCompat
import kotlinx.coroutines.flow.first


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


    fun signIn(emailAddress: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.signIn(emailAddress, password).collect { signInSuccess ->
                    when {
                        !isNetworkAvailable() -> {
                            _signInResult.postValue(SignInResult.NETWORK_ERROR)
                        }
                        signInSuccess -> {
                            _signInResult.postValue(SignInResult.SUCCESS)
                            authRepository.getUserAccountByEmailAddress(emailAddress)
                                .collect { userAccount ->
                                    _currentUser.value = userAccount
                                    userAccount?.let {
                                        val token = UUID.randomUUID().toString()
                                        sessionManager.createLoginSession(it.emailAddress, token)
                                        _isLoggedIn.value = true
                                    }
                                }
                        }
                        else -> {
                            _signInResult.postValue(SignInResult.INVALID_CREDENTIALS)
                            _isLoggedIn.value = false
                            _currentUser.value = null
                        }
                    }
                }
            } catch (e: Exception) {
                // Post an unknown error result when an exception occurs
                _signInResult.postValue(SignInResult.UNKNOWN_ERROR)
                _isLoggedIn.value = false
                _currentUser.value = null
            }
        }
    }

    fun signUp(emailAddress: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            when {
                emailAddress.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                    _signUpResult.postValue(SignUpResult.MISSING_INFORMATION)
                }
                !isValidEmail(emailAddress) -> {
                    _signUpResult.postValue(SignUpResult.INVALID_EMAIL)
                }
                password.length < 8 -> {
                    _signUpResult.postValue(SignUpResult.SHORT_PASSWORD)
                }
                !password.matches(Regex(".*[A-Z].*")) -> {
                    _signUpResult.postValue(SignUpResult.MISSING_CAPITAL_LETTER)
                }
                !password.matches(Regex(".*[a-z].*")) -> {
                    _signUpResult.postValue(SignUpResult.MISSING_LOWCASE_LETTER)
                }
                !password.matches(Regex(".*\\d.*")) -> {
                    _signUpResult.postValue(SignUpResult.MISSING_DIGIT)
                }
                password != confirmPassword -> {
                    _signUpResult.postValue(SignUpResult.PASSWORD_NO_MATCH)
                }
                !isNetworkAvailable() -> {
                    _signUpResult.postValue(SignUpResult.NETWORK_ERROR)
                }
                else -> {
                    authRepository.isEmailExisting(emailAddress).collect { emailExists ->
                        if (emailExists) {
                            _signUpResult.postValue(SignUpResult.EXISTING_EMAIL)
                        } else {
                            authRepository.signUp(emailAddress, password).collect { success ->
                                if (success) {
                                    _signUpResult.postValue(SignUpResult.SUCCESS)
                                    val token = UUID.randomUUID().toString()
                                    sessionManager.createLoginSession(emailAddress, token)
                                    _currentUser.value = UserAccount(emailAddress = emailAddress, password = password) // Simplified version
                                    _isLoggedIn.value = true
                                } else {
                                    _signUpResult.postValue(SignUpResult.UNKNOWN_ERROR)
                                    _isLoggedIn.value = false
                                    _currentUser.value = null
                                }
                            }
                        }
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

    // Network availability check function
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Email existence check function
    private suspend fun isEmailExisting(email: String): Boolean {
        return authRepository.isEmailExisting(email).first()
    }
    // Additional ViewModel logic...

    // Function to validate email
    private fun isValidEmail(email: String): Boolean {

        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}

