package com.example.skyreserve.UI

import LocalSessionManager
import UserViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.Repository.AuthRepository

class UserViewModelFactory(private val authRepository: AuthRepository,
                           private val localSessionManager: LocalSessionManager,
                           private val context: Context)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(authRepository, localSessionManager, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
