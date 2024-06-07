package com.example.skyreserve.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.database.room.dao.UserAccountDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAccountRepository @Inject constructor(private val userAccountDao: UserAccountDao) {

//    private val allUsersAccounts: LiveData<List<UserAccount>> = userAccountDao.getAllUsersFlow()
//        .flowOn(Dispatchers.IO)
//        .asLiveData()

    // Repository Methods
    // MAYBE HAVE AUTHREPO USE THIS
    suspend fun insertUserAccount(userAccount: UserAccount): Boolean {
        return try {
            userAccountDao.insertUserAccount(userAccount)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUserAccount(userAccount: UserAccount): Boolean {
        return try {
            userAccountDao.updateUserAccount(userAccount)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUserAccount(userAccount: UserAccount): Boolean {
        return try {
            userAccountDao.deleteUserAccount(userAccount)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount? {
        return userAccountDao.getUserAccountByEmailAddress(emailAddress)
    }


//    fun getAllUsers(): LiveData<List<UserAccount>> {
//        return allUsersAccounts
//    }

    // ... other repository methods
}

