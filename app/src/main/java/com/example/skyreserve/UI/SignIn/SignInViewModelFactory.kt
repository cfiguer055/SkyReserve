package com.example.skyreserve.UI.SignIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.Repository.AuthRepository


// put into constructor later
// private val authRepository: AuthRepository
class SignInViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
//            return SignInViewModel(authRepository) as T
            return SignInViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}