package com.example.skyreserve.UI.Home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.Repository.DatabaseRepository.UserAccountRepository
import com.example.skyreserve.Util.LocalSessionManager

class HomeViewModelFactory(private val userAccountRepository: UserAccountRepository,
                           private val localSessionManager: LocalSessionManager,
                           private val context: Context)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserAccountViewModel::class.java)) {
            return UserAccountViewModel(userAccountRepository, localSessionManager, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}