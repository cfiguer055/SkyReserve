package com.example.skyreserve.UI

import UserViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.UI.Home.HomeActivity
import com.example.skyreserve.Util.LocalSessionManager

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
