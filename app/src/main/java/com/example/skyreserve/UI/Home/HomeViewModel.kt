package com.example.skyreserve.UI.Home

import android.content.Context
import com.example.skyreserve.Util.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.Repository.DatabaseRepository.UserAccountRepository
import com.example.skyreserve.Util.LocalSessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val userAccountRepository: UserAccountRepository,
                    private val sessionManager: LocalSessionManager, // Manages local session state
                    private val context: Context)
    : ViewModel() {
    private val _userName = MutableLiveData<String?>()
    val userName: MutableLiveData<String?> = _userName

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus


    fun fetchUserDetails(email: String) {
        viewModelScope.launch {
            val userAccount = withContext(Dispatchers.IO) {
                userAccountRepository.getUserAccountByEmailAddress(email)
            }
            _userName.postValue(userAccount?.firstName) // update LiveData on the main thread
        }
    }

    fun updateUserDetails(userData: UserData) { // UserData is a data class holding user details
        viewModelScope.launch(Dispatchers.IO) {
            val userEmail = sessionManager.getUserEmail()
            val userAccount = userEmail?.let { userAccountRepository.getUserAccountByEmailAddress(it) }

            if (userAccount != null) {
                // Update the user account object with userData fields
                userAccount.apply {
                    firstName = userData.firstName
                    lastName = userData.lastName
                    gender = userData.gender
                    phone = userData.phone
                    dateOfBirth = userData.dateOfBirth
                    address = userData.address
                    stateCode = userData.stateCode
                    countryCode = userData.countryCode
                    passport = userData.passport
                    // ... set other fields as necessary
                }

                userAccountRepository.updateUserAccount(userAccount)
                _updateStatus.postValue(true) // Notify that update is successful
            } else {
                _updateStatus.postValue(false) // Notify about the failure
            }
        }
    }
}

//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            val userEmail = withContext(Dispatchers.IO) {
//                sessionManager.getUserEmail()
//            }
//
//            if (userEmail != null) {
//                val userAccount = withContext(Dispatchers.IO) {
//                    userAccountRepository.getUserAccountByEmailAddress(userEmail)
//                }
//
//                if (userAccount != null) {
//                    // Update the user account object with userData fields
//                    userAccount.firstName = userData.firstName
//                    userAccount.lastName = userData.lastName
//                    userAccount.gender = userData.gender
//                    userAccount.phone = userData.phone
//                    userAccount.dateOfBirth = userData.dateOfBirth
//                    userAccount.address = userData.address
//                    userAccount.stateCode = userData.stateCode
//                    userAccount.countryCode = userData.countryCode
//                    userAccount.passport = userData.passport
//                    // ... set other fields as necessary
//
//                    withContext(Dispatchers.IO) {
//                        userAccountRepository.updateUserAccount(userAccount)
//                    }
//                    _updateStatus.postValue(true) // Notify that update is successful
//                } else {
//                    _updateStatus.postValue(false) // Notify about the failure
//                }
//            } else {
//                _updateStatus.postValue(false) // Notify about the failure
//            }
//        }
//    }
