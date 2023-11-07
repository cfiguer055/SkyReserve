package com.example.skyreserve.UI.SignUp

import androidx.lifecycle.ViewModel
import com.example.skyreserve.Utility.SignUpResult

/*
This ViewModel handles the logic for signing up a new user.
It should contain LiveData for the sign-up result, such as success or error messages.
It communicates with the Sign-Up Repository.
*/

// ADD THIS TO CONSTRUCTOR LATER
// private val authRepository: AuthRepository
class SignUpViewModel() : ViewModel() {

    // WILL BE USED LATER
//    private val _signUpResult = MutableLiveData<Boolean>()
//    val signUpResult: LiveData<Boolean> get() = _signUpResult
//
//    suspend fun signUp(emailAddress: String, password: String, firstName: String, lastName: String) {
//        // Perform sign-up logic here, then update _signUpResult
//        val success = authRepository.signUp(emailAddress, password, firstName, lastName)
//        _signUpResult.postValue(success)
//    }
//
//    // Check database if email exists already and if format is correct
//    suspend fun validateEmail(emailAddress: String) : Boolean {
//        return true
//    }
//
//    // may not need suspend because no database interaction
//    suspend fun validatePassword(password: String, confirmPassword: String) : Boolean {
//        return password == confirmPassword
//        // ADD CHECKS FOR PASSWORD TYPES AND VALID CHARACTERS
//    }

    // temp
    fun signUp(email: String, password: String, confirmPassword: String, callback: (SignUpResult) -> Unit) {
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
                // Attempt to create a new user account and handle success or unknown error
                createUserAccount(email, password) { success ->
                    if (success) {
                        callback(SignUpResult.SUCCESS)
                    } else {
                        callback(SignUpResult.UNKNOWN_ERROR)
                    }
                }
            }
        }
    }

    // Mock functions to illustrate the concept - replace these with your actual implementation
    fun isEmailExisting(email: String): Boolean {
        // Replace with actual email existence check
        return false
    }

    fun isNetworkAvailable(): Boolean {
        // Replace with actual network status check
        return true
    }

    // This will be replaced with database authentication later
    fun createUserAccount(email: String, password: String, result: (Boolean) -> Unit) {
        // Replace with actual sign-up logic and call 'result' with true if successful, false otherwise
        val accountCreationSuccess = true
        result(accountCreationSuccess)
    }

}
