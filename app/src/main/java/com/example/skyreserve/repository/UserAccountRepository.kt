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
//    private val userAccountDao: UserAccountDao
//    private val allUsersAccounts: LiveData<List<UserAccount>>
//    // ... other properties
//
//    init {
//        val database = SkyReserveDatabase.getInstance(application)
//        userAccountDao = database.userAccountDao()
//        allUsersAccounts = userAccountDao.getAllUsersFlow()
//            .flowOn(Dispatchers.IO)
//            .asLiveData()
//        // initialize other properties
//    }

    private val allUsersAccounts: LiveData<List<UserAccount>> = userAccountDao.getAllUsersFlow()
        .flowOn(Dispatchers.IO)
        .asLiveData()

    // Repository Methods
    // MAYBE HAVE AUTHREPO USE THIS
    suspend fun insertUserAccount(userAccount: UserAccount) {
        userAccountDao.insertUserAccount(userAccount)
    }

    suspend fun updateUserAccount(userAccount: UserAccount) {
        userAccountDao.updateUserAccount(userAccount)
    }

    suspend fun deleteUserAccount(userAccount: UserAccount) {
        userAccountDao.deleteUserAccount(userAccount)
    }

    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount? {
        return userAccountDao.getUserAccountByEmailAddress(emailAddress)
    }


    fun getAllUsers(): LiveData<List<UserAccount>> {
        return allUsersAccounts
    }

    // ... other repository methods
}

