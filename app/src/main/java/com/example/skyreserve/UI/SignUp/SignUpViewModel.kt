package com.example.skyreserve.UI.SignUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skyreserve.Repository.AuthRepository

/*
This ViewModel handles the logic for signing up a new user.
It should contain LiveData for the sign-up result, such as success or error messages.
It communicates with the Sign-Up Repository.
*/
class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult

    suspend fun signUp(emailAddress: String, password: String, firstName: String, lastName: String) {
        // Perform sign-up logic here, then update _signUpResult
        val success = authRepository.signUp(emailAddress, password, firstName, lastName)
        _signUpResult.postValue(success)
    }

    // Check database if email exists already and if format is correct
    suspend fun validateEmail(emailAddress: String) : Boolean {
        return true
    }

    // may not need suspend because no database interaction
    suspend fun validatePassword(password: String, confirmPassword: String) : Boolean {
        return password == confirmPassword
        // ADD CHECKS FOR PASSWORD TYPES AND VALID CHARACTERS
    }
}
