package com.example.skyreserve.ui.account

import android.content.Context
import com.example.skyreserve.util.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


//    fun fetchUserDetails(email: String) {
//        viewModelScope.launch {
//            try {
//                // Assuming we are back to not using Dispatchers.IO for simplicity here.
//                val userAccount = userAccountRepository.getUserAccountByEmailAddress(email)
//
//                val userData = userAccount?.run {
//                    UserData(
//                        firstName = firstName ?: "",
//                        lastName = lastName ?: "",
//                        gender = gender ?: "",
//                        phone = phone ?: "",
//                        dateOfBirth = dateOfBirth ?: "",
//                        address = address ?: "",
//                        stateCode = stateCode ?: "",
//                        countryCode = countryCode ?: "",
//                        passport = passport ?: ""
//                    )
//                }
//                _userDetails.value = userData
//            } catch (e: Exception) {
//                System.out.println("error: ${e.message}")
//            }
//        }
//    }
//    fun fetchUserDetails(email: String) {
//        viewModelScope.launch {
//            userAccountRepository.getUserAccountByEmailAddress(email).collect { userAccount ->
//                // Check for null and update LiveData
//                val userData = userAccount?.run {
//                    UserData(
//                        firstName = firstName ?: "",
//                        lastName = lastName ?: "",
//                        gender = gender ?: "",
//                        phone = phone ?: "",
//                        dateOfBirth = dateOfBirth ?: "",
//                        address = address ?: "",
//                        stateCode = stateCode ?: "",
//                        countryCode = countryCode ?: "",
//                        passport = passport ?: ""
//                    )
//                }
//                _userDetails.value = userData
//            }
//        }
//    }

    fun fetchUserDetails(email: String) {
        val userAccountLiveData = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
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

    //  TRY TESTING WITH 'viewModelScope.launch(Dispatchers.IO)'
    // TO SEE IF THAT WAS ISSUE LAST NIGHT

//    fun updateUserDetails(userData: UserData) { // UserData is a data class holding user details
//        viewModelScope.launch {
//            val userEmail = sessionManager.getUserEmail()
//            val userAccount =
//                userEmail?.let { userAccountRepository.getUserAccountByEmailAddress(it) }
//
//            if (userAccount != null) {
//                // Update the user account object with userData fields
//                userAccount.apply {
//                    firstName = userData.firstName
//                    lastName = userData.lastName
//                    gender = userData.gender
//                    phone = userData.phone
//                    dateOfBirth = userData.dateOfBirth
//                    address = userData.address
//                    stateCode = userData.stateCode
//                    countryCode = userData.countryCode
//                    passport = userData.passport
//                    // ... set other fields as necessary
//                }
//
//                val success = userAccountRepository.updateUserAccount(userAccount)
//
//                _updateStatus.value = success // Notify that update is successful
//                _userDetails.value = if (success) userData else null
//            } else {
//                _updateStatus.value = false // Notify about the failure
//                _userDetails.value = null
//            }
//        }
//    }

//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            val userEmail = sessionManager.getUserEmail()
//            userEmail?.let {
//                userAccountRepository.getUserAccountByEmailAddress(it).collect { userAccount ->
//                    if (userAccount != null) {
//                        // Update the user account object with userData fields
//                        userAccount.apply {
//                            firstName = userData.firstName
//                            lastName = userData.lastName
//                            gender = userData.gender
//                            phone = userData.phone
//                            dateOfBirth = userData.dateOfBirth
//                            address = userData.address
//                            stateCode = userData.stateCode
//                            countryCode = userData.countryCode
//                            passport = userData.passport
//                        }
//                        val success = userAccountRepository.updateUserAccount(userAccount)
//                        _updateStatus.value = success // Notify that update is successful
//                        _userDetails.value = if (success) userData else null
//                    } else {
//                        _updateStatus.value = false // Notify about the failure
//                        _userDetails.value = null
//                    }
//                }
//            }
//        }
//    }

//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            sessionManager.getUserEmail()?.let { userEmail ->
//                // Asynchronously fetch the user account
//                userAccountRepository.getUserAccountByEmailAddress(userEmail).collect { userAccount ->
//                    if (userAccount != null) {
//                        // Update the user account object with new user details
//                        userAccount.apply {
//                            firstName = userData.firstName
//                            lastName = userData.lastName
//                            gender = userData.gender
//                            phone = userData.phone
//                            dateOfBirth = userData.dateOfBirth
//                            address = userData.address
//                            stateCode = userData.stateCode
//                            countryCode = userData.countryCode
//                            passport = userData.passport
//                        }
//
//                        // Try to update the user account in the database
//                        val success = userAccountRepository.updateUserAccount(userAccount)
//                        _updateStatus.postValue(success) // Notify that update is successful
//                        _userDetails.postValue(if (success) userData else null)
//                    } else {
//                        _updateStatus.postValue(false) // Notify about the failure
//                        _userDetails.postValue(null)
//                    }
//                }
//            } ?: run {
//                _updateStatus.postValue(false) // Email is null, post failure
//                _userDetails.postValue(null)
//            }
//        }
//    }

//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            val userEmail = sessionManager.getUserEmail()
//            userEmail?.let { email ->
//                val userAccount = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
//                if (userAccount != null) {
//                    // Apply new data to the fetched user account
//                    val updatedUserAccount = userAccount.apply {
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
//                    // Attempt to update the user account in the repository
//                    val success = userAccountRepository.updateUserAccount(updatedUserAccount)
//                    _updateStatus.postValue(success)
//                    _userDetails.postValue(if (success) userData else null)
//                } else {
//                    _updateStatus.postValue(false)
//                    _userDetails.postValue(null)
//                }
//            } ?: run {
//                // No email found in session, post failure
//                _updateStatus.postValue(false)
//                _userDetails.postValue(null)
//            }
//        }
//    }


//    fun updateUserDetails(userData: UserData) {
//        viewModelScope.launch {
//            val userEmail = sessionManager.getUserEmail()
//            userEmail?.let { email ->
//                userAccountRepository.getUserAccountByEmailAddress(email).collect { userAccount ->
//                    if (userAccount != null) {
//                        userAccount.apply {
//                            firstName = userData.firstName
//                            lastName = userData.lastName
//                            gender = userData.gender
//                            phone = userData.phone
//                            dateOfBirth = userData.dateOfBirth
//                            address = userData.address
//                            stateCode = userData.stateCode
//                            countryCode = userData.countryCode
//                            passport = userData.passport
//                        }
//                        val success = userAccountRepository.updateUserAccount(userAccount)
//                        _updateStatus.value = success
//                        _userDetails.value = if (success) userData else null
//                    } else {
//                        _updateStatus.value = false
//                        _userDetails.value = null
//                    }
//                }
//            }
//        }
//    }

    fun updateUserDetails(userData: UserData) {
        viewModelScope.launch {
            val userEmail = sessionManager.getUserEmail()
            if (userEmail == null) {
                _updateStatus.value = false
                _userDetails.value = null
                return@launch
            }

            userAccountRepository.getUserAccountByEmailAddress(userEmail).collect { userAccount ->
                if (userAccount != null) {
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
                    }
                    val success = userAccountRepository.updateUserAccount(userAccount)
                    _updateStatus.value = success
                    _userDetails.value = if (success) userData else null
                } else {
                    _updateStatus.value = false
                    _userDetails.value = null
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


//    fun fetchUserDetails(email: String) {
//        System.out.println("1")
//        viewModelScope.launch {
//            System.out.println("2")
//            System.out.println("3")
//            val userAccount = withContext(Dispatchers.IO) {
//                userAccountRepository.getUserAccountByEmailAddress(email)
//            }
//            System.out.println("4")
//            Log.d("UserAccountViewModel", "userAccount: $userAccount")
//            System.out.println("userAccount: $userAccount")
//
//            System.out.println("5")
//            _userDetails.value = userAccount?.run {
//                UserData(
//                    firstName = userAccount.firstName ?: "",
//                    lastName = userAccount.lastName ?: "",
//                    gender = userAccount.gender ?: "",
//                    phone = userAccount.phone ?: "",
//                    dateOfBirth = userAccount.dateOfBirth ?: "",
//                    address = userAccount.address ?: "",
//                    stateCode = userAccount.stateCode ?: "",
//                    countryCode = userAccount.countryCode ?: "",
//                    passport = userAccount.passport ?: ""
//                ).toString()
//            }
//
//            System.out.println("6")
//            Log.d("UserAccountViewModel", "userAccount: ${_userDetails.value}")
//            System.out.println("userAccount: ${_userDetails.value}")
//        }
//
////        viewModelScope.launch {
////            val userAccount = withContext(Dispatchers.IO) {
////                userAccountRepository.getUserAccountByEmailAddress(email)
////            }
////            _userDetails.postValue(userAccount?.firstName) // update LiveData on the main thread
////        }
//    }

