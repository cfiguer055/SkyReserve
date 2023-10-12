package com.example.skyreserve.UI.SignIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skyreserve.Repository.AuthRepository

/*
This ViewModel handles the logic for signing in a user.
It should contain LiveData for the sign-in result, such as success or error messages.
It communicates with the Sign-In Repository.
*/
class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean> get() = _signInResult

    suspend fun signIn(username: String, password: String) {
        // Perform sign-in logic here, then update _signInResult
        val success = authRepository.signIn(username, password)
        _signInResult.postValue(success)
    }
}