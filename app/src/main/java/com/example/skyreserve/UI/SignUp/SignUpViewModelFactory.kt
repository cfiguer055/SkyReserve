package com.example.skyreserve.UI.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.Repository.AuthRepository

// ADD THIS TO CONSTRUCTOR LATER
// private val authRepository: AuthRepository
class SignUpViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel() as T
//            return SignUpViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}