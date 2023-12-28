package com.example.skyreserve.UI.SignUp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.Util.SignUpResult

/*
This ViewModel handles the logic for signing up a new user.
It should contain LiveData for the sign-up result, such as success or error messages.
It communicates with the Sign-Up Repository.
*/

// ADD THIS TO CONSTRUCTOR LATER
// private val authRepository: AuthRepository
class SignUpViewModel(private val authRepository: AuthRepository, private val context: Context) : ViewModel() {
    // temp
    suspend fun signUp(email: String, password: String, confirmPassword: String, callback: (SignUpResult) -> Unit) {
        when {
            email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                callback(SignUpResult.MISSING_INFORMATION)
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                callback(SignUpResult.INVALID_EMAIL)
            }
            isEmailExisting(email) -> {
                callback(SignUpResult.EXISTING_EMAIL)
            }
            password.length < 8 -> {
                callback(SignUpResult.SHORT_PASSWORD)
            }
            !password.matches(Regex(".*[A-Z].*")) -> {
                callback(SignUpResult.MISSING_CAPITAL_LETTER)
            }
            !password.matches(Regex(".*[a-z].*")) -> {
                callback(SignUpResult.MISSING_LOWCASE_LETTER)
            }
            !password.matches(Regex(".*\\d.*")) -> { // checks for at least one digit
                callback(SignUpResult.MISSING_DIGIT)
            }
            password != confirmPassword -> {
                callback(SignUpResult.PASSWORD_NO_MATCH)
            }
            !isNetworkAvailable() -> {
                callback(SignUpResult.NETWORK_ERROR)
            }
            // If all checks pass, then proceed with the actual sign up process
            else -> {
                val success = authRepository.signUp(email, password)
                callback(if (success) SignUpResult.SUCCESS else SignUpResult.UNKNOWN_ERROR)
            }
        }
    }

    // Mock functions to illustrate the concept - replace these with your actual implementation
    suspend fun isEmailExisting(email: String): Boolean {
        return authRepository.isEmailExisting(email)
    }

    fun isNetworkAvailable(): Boolean {
        // Replace with actual network status check
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }
        return false
    }

    // This will be replaced with database authentication later
//    fun createUserAccount(email: String, password: String, result: (Boolean) -> Unit) {
//        // Replace with actual sign-up logic and call 'result' with true if successful, false otherwise
//        val accountCreationSuccess = true
//        result(accountCreationSuccess)
//    }

}
