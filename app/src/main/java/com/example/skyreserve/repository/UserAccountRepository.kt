package com.example.skyreserve.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.database.room.dao.UserAccountDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAccountRepository @Inject constructor(private val userAccountDao: UserAccountDao) {

    /**
     * Retrieves a flow of all user accounts from the database.
     * The data is fetched on the IO dispatcher for better performance and thread safety.
     */
    fun getAllUsersAccounts(): Flow<List<UserAccount>> = userAccountDao.getAllUsersFlow()
        .flowOn(Dispatchers.IO)

    /**
     * Inserts a user account into the database.
     * The operation is performed on the IO dispatcher and a Boolean result is returned indicating success.
     */
    suspend fun insertUserAccount(userAccount: UserAccount): Boolean = flow {
        try {
            userAccountDao.insertUserAccount(userAccount)
            emit(true)  // Emit true if the insertion is successful
        } catch (e: Exception) {
            emit(false)  // Emit false if an error occurs
        }
    }.flowOn(Dispatchers.IO).single()

    /**
     * Updates a user account in the database.
     * This function also runs on the IO dispatcher and returns a Boolean result.
     */
    suspend fun updateUserAccount(userAccount: UserAccount): Boolean = flow {
        try {
            userAccountDao.updateUserAccount(userAccount)
            emit(true)  // Emit true if the update is successful
        } catch (e: Exception) {
            emit(false)  // Emit false if an error occurs
        }
    }.flowOn(Dispatchers.IO).single()

    /**
     * Deletes a user account from the database.
     * This function returns a Boolean to indicate whether the deletion was successful.
     */
    suspend fun deleteUserAccount(userAccount: UserAccount): Boolean = flow {
        try {
            userAccountDao.deleteUserAccount(userAccount)
            emit(true)  // Emit true if the deletion is successful
        } catch (e: Exception) {
            emit(false)  // Emit false if an error occurs
        }
    }.flowOn(Dispatchers.IO).single()

    /**
     * Fetches a user account by email address from the database.
     * The result is emitted as a Flow of UserAccount, which may be null if the account does not exist.
     */
    fun getUserAccountByEmailAddress(emailAddress: String): Flow<UserAccount?> = flow {
        emit(userAccountDao.getUserAccountByEmailAddress(emailAddress))
    }.flowOn(Dispatchers.IO)

    // Example of how to convert this flow to LiveData if needed in the ViewModel
    fun getUserAccountByEmailAddressAsLiveData(emailAddress: String): LiveData<UserAccount?> =
        getUserAccountByEmailAddress(emailAddress).asLiveData()
}


