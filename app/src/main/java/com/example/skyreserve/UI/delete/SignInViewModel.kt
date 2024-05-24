package com.example.skyreserve.UI.delete

import androidx.lifecycle.ViewModel
import com.example.skyreserve.Util.SignInResult

/*
This ViewModel handles the logic for signing in a user.
It should contain LiveData for the sign-in result, such as success or error messages.
It communicates with the Sign-In Repository.
*/

// Add this to Constructor later
// private val authRepository: AuthRepository
class SignInViewModel() : ViewModel() {

    // WILL BE USED LATER
//    private val _signInResult = MutableLiveData<Boolean>()
//    val signInResult: LiveData<Boolean> get() = _signInResult
//
//    suspend fun signIn(username: String, password: String) {
//        // Perform sign-in logic here, then update _signInResult
//        val success = authRepository.signIn(username, password)
//        _signInResult.postValue(success)
//    }

    // temp
    fun signIn(email: String, password: String, callback: (SignInResult) -> Unit) {
        // Mock some conditions for different results
        when {
            // Condition 1: Valid email format and correct password
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                    password.equals("password", ignoreCase = true) -> {
                callback(SignInResult.SUCCESS)
            }

            // Condition 2: Invalid email format or incorrect password
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ||
                    !password.equals("password", ignoreCase = true) -> {
                callback(SignInResult.INVALID_CREDENTIALS)
            }

            // You can add more conditions here if needed
            else -> {
                callback(SignInResult.UNKNOWN_ERROR)
            }
        }

    }
}