package com.example.skyreserve.Repository

import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.Entity.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val userAccountDao: UserAccountDao) {

    // Perform user sign-in
    suspend fun signIn(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Query the local database to find the user by username and password
                val userAccount = userAccountDao.getUserAccountByUsername(username)

                return@withContext userAccount != null && userAccount.password == password
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors)
                return@withContext false
            }
        }
    }

    // Perform user sign-up
    suspend fun signUp(
        username: String,
        password: String,
        firstName: String,
        lastName: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Check if the user with the same username already exists in the database
                val existingUser = userAccountDao.getUserAccountByUsername(username)

                if (existingUser == null) {
                    // Create a new UserAccount entity and insert it into the database
                    val newUserAccount = UserAccount(
                        username = username,
                        password = password,
                        firstName = firstName,
                        lastName = lastName
                    )
                    userAccountDao.insertUserAccount(newUserAccount)
                    return@withContext true // Sign-up successful
                } else {
                    return@withContext false // User with the same username already exists
                }
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors)
                return@withContext false
            }
        }
    }

    // Additional methods can be added for managing user sessions, tokens, etc.
}
