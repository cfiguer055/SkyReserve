package com.example.skyreserve.ui.account

import android.content.Context
import androidx.lifecycle.*
import com.example.skyreserve.util.UserData
import com.example.skyreserve.repository.UserAccountRepository
import com.example.skyreserve.util.LocalSessionManager
import com.example.skyreserve.database.room.entity.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAccountViewModel @Inject constructor (
    private val userAccountRepository: UserAccountRepository,
    private val sessionManager: LocalSessionManager, // Manages local session state
    @ApplicationContext private val context: Context
    ) : ViewModel() {


    private val _userDetails = MutableLiveData<UserData?>()
    val userDetails: MutableLiveData<UserData?> = _userDetails

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus



    fun fetchUserDetails(email: String) {
        val userAccountLiveData = userAccountRepository.getUserAccountByEmailAddress(email).asLiveData()
        userAccountLiveData.observeForever { userAccount ->
            val userData = userAccount?.let {
                UserData(
                    firstName = it.firstName ?: "",
                    lastName = it.lastName ?: "",
                    gender = it.gender ?: "",
                    phone = it.phone ?: "",
                    dateOfBirth = it.dateOfBirth ?: "",
                    address = it.address ?: "",
                    stateCode = it.stateCode ?: "",
                    countryCode = it.countryCode ?: "",
                    passport = it.passport ?: ""
                )
            }
            _userDetails.value = userData
        }
    }


    // Insert both New User Account and Optional Personal Data
    // This is more for adminstrative purposes as AuthRepo deals with creating new users and not UserAccountRepo
    // ByPass SignUp
    fun insertUserDetails(email: String, password: String, userData: UserData) {
        viewModelScope.launch {
            val userEmail = sessionManager.getUserEmail()
            if (userEmail == null) {
                val userAccount = UserAccount(email, password, userData)
                val success = userAccountRepository.insertUserAccount(userAccount)

                _updateStatus.value = if (success) success else false
                _userDetails.value = if (success) userData else null
                success
            } else {
                _updateStatus.value = false
                _userDetails.value = null
                false
            }
        }
    }


//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            val userEmail = sessionManager.getUserEmail()
//            if (userEmail == null) {
//                _updateStatus.value = false
//                _userDetails.value = null
//                return@launch
//            }
//
//            userAccountRepository.getUserAccountByEmailAddress(userEmail).collect { userAccount ->
//                if (userAccount != null) {
//                    userAccount.apply {
//                        firstName = userData.firstName
//                        lastName = userData.lastName
//                        gender = userData.gender
//                        phone = userData.phone
//                        dateOfBirth = userData.dateOfBirth
//                        address = userData.address
//                        stateCode = userData.stateCode
//                        countryCode = userData.countryCode
//                        passport = userData.passport
//                    }
//                    val success = userAccountRepository.updateUserAccount(userAccount)
//                    _updateStatus.value = success
//                    _userDetails.value = if (success) userData else null
//                } else {
//                    _updateStatus.value = false
//                    _userDetails.value = null
//                }
//            }
//        }
//    }

//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            val userEmail = sessionManager.getUserEmail()
//            if (userEmail == null) {
//                _updateStatus.value = false
//                _userDetails.value = null
//            } else {
//                val userAccount = userAccountRepository.getUserAccountByEmailAddress(userEmail).asLiveData()
//                if (userAccount != null) {
//                    // Since UserAccount properties should be mutable (declared with 'var'), we can modify them directly
//                    userAccount.firstName = userData.firstName
//                    userAccount.lastName = userData.lastName
//                    userAccount.gender = userData.gender
//                    userAccount.phone = userData.phone
//                    userAccount.dateOfBirth = userData.dateOfBirth
//                    userAccount.address = userData.address
//                    userAccount.stateCode = userData.stateCode
//                    userAccount.countryCode = userData.countryCode
//                    userAccount.passport = userData.passport
//
//                    // Attempt to update the user account in the database
//                    val success = userAccountRepository.updateUserAccount(userAccount)
//                    _updateStatus.value = success
//                    _userDetails.value = if (success) userData else null
//                } else {
//                    _updateStatus.value = false
//                    _userDetails.value = null
//                }
//            }
//        }
//    }
    fun updateUserDetails(userData: UserData) {
        viewModelScope.launch {
            val userEmail = sessionManager.getUserEmail()
            if (userEmail == null) {
                _updateStatus.postValue(false)
                _userDetails.postValue(null)
            } else {
                try {
                    userAccountRepository.getUserAccountByEmailAddress(userEmail)
                        .collect { userAccount ->
                            userAccount?.let {
                                // Update the user account properties
                                it.firstName = userData.firstName
                                it.lastName = userData.lastName
                                it.gender = userData.gender
                                it.phone = userData.phone
                                it.dateOfBirth = userData.dateOfBirth
                                it.address = userData.address
                                it.stateCode = userData.stateCode
                                it.countryCode = userData.countryCode
                                it.passport = userData.passport

                                // Attempt to update the user account in the database
                                val success = userAccountRepository.updateUserAccount(it)
                                _updateStatus.postValue(success)
                                _userDetails.postValue(if (success) userData else null)
                            } ?: run {
                                _updateStatus.postValue(false)
                                _userDetails.postValue(null)
                            }
                        }
                } catch(e: Exception) {
                    _updateStatus.postValue(false)
                    _userDetails.postValue(null)
                }
            }
        }
    }




    fun validateSession() : Boolean {
        return sessionManager.isTokenValid()
    }

    fun getUserEmail(): String? {
        if (validateSession()) {
            return sessionManager.getUserEmail()
        }
        return null
    }

    fun clear() {
        _userDetails.value = null
        _updateStatus.value = false
    }

}


