package com.example.skyreserve.Repository.DatabaseRepository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.skyreserve.Database.Dao.*
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Database.SkyReserveDatabase
import com.example.skyreserve.UI.Home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class UserAccountRepository(application: Application) {
    private val userAccountDao: UserAccountDao
    private val allUsersAccounts: LiveData<List<UserAccount>>
    // ... other properties

    init {
        val database = SkyReserveDatabase.getInstance(application)
        userAccountDao = database.userAccountDao()
        allUsersAccounts = userAccountDao.getAllUsersFlow()
            .flowOn(Dispatchers.IO)
            .asLiveData()
        // initialize other properties
    }

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

