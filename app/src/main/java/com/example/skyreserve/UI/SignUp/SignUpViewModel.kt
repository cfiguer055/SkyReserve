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

    suspend fun signUp(username: String, password: String, firstName: String, lastName: String) {
        // Perform sign-up logic here, then update _signUpResult
        val success = authRepository.signUp(username, password, firstName, lastName)
        _signUpResult.postValue(success)
    }
}
