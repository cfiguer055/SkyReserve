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

/**
 * ViewModel for managing user account details.
 * Handles fetching, inserting, and updating user details.
 *
 * @property userAccountRepository Repository for user account data operations.
 * @property sessionManager Manager for handling local session state.
 * @property context Application context for accessing resources.
 */
@HiltViewModel
class UserAccountViewModel @Inject constructor (
    private val userAccountRepository: UserAccountRepository,
    private val sessionManager: LocalSessionManager, // Manages local session state
    @ApplicationContext private val context: Context
    ) : ViewModel() {

    /**
     * LiveData for user details.
     */
    private val _userDetails = MutableLiveData<UserData?>()
    val userDetails: MutableLiveData<UserData?> = _userDetails

    /**
     * LiveData for tracking the update status.
     */
    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus


    /**
     * Fetches user details by email and updates LiveData.
     * Observes the data from the repository and maps it to UserData.
     *
     * @param email User email to fetch details.
     */
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


    /**
     * Inserts new user details into the database.
     * Updates the LiveData based on the success of the operation.
     *
     * @param email User email to insert.
     * @param password User password to insert.
     * @param userData User data to insert.
     */
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

    /**
     * Updates existing user details in the database.
     * Updates the LiveData based on the success of the operation.
     *
     * @param userData User data to update.
     */
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


    /**
     * Validates the current session.
     *
     * @return True if the session is valid, false otherwise.
     */
    fun validateSession() : Boolean {
        return sessionManager.isTokenValid()
    }

    /**
     * Retrieves the user email if the session is valid.
     *
     * @return User email if session is valid, null otherwise.
     */
    fun getUserEmail(): String? {
        if (validateSession()) {
            return sessionManager.getUserEmail()
        }
        return null
    }

    /**
     * Clears the user details and update status.
     */
    fun clear() {
        _userDetails.value = null
        _updateStatus.value = false
    }

}


