package com.example.skyreserve.Repository

import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.Entity.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Singleton

// LATER BACKEND IMPLEMENTATION
// This class will need significant modifications. Instead of directly querying the local database for
// user authentication, it will make network requests to the backend for user sign-in, sign-up, and
// token validation.
@Singleton
class AuthRepository(private val userAccountDao: UserAccountDao) {

    // Perform user sign-in
    suspend fun signIn(emailAddress: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Query the local database to find the user by username and password
                val userAccount = userAccountDao.getUserAccountByEmailAddress(emailAddress)

                // This can get easily hacked with console.log. Add security measures or 3rd party server deals with it.

                return@withContext userAccount != null && userAccount.password == password
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors)
                return@withContext false
            }
        }
    }

    // Perform user sign-up
    suspend fun signUp(
        emailAddress: String,
        password: String,
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Check if the user with the same username already exists in the database
                val existingUser = userAccountDao.getUserAccountByEmailAddress(emailAddress)

                if (existingUser == null) {
                    // Create a new UserAccount entity and insert it into the database
                    val newUserAccount = UserAccount(
                        emailAddress = emailAddress,
                        password = password
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

    // Check if an email exists in the database
    suspend fun isEmailExisting(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userAccount = userAccountDao.getUserAccountByEmailAddress(email)
                return@withContext userAccount != null
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors)
                return@withContext false
            }
        }
    }

    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount? {
        return userAccountDao.getUserAccountByEmailAddress(emailAddress)
    }

    // Helper functions for password hashing and checking
    private fun hashPassword(password: String): String {
        // TODO: Implement password hashing
        return ""
    }

    private fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
        // TODO: Implement password checking against hashed password
        return false
    }


    // Additional methods can be added for managing user sessions, tokens, etc.
}


// FOR SERVER BASED
//suspend fun signUp(
//    emailAddress: String,
//    password: String,
//    firstName: String,
//    lastName: String
//): SignUpResult {
//    return withContext(Dispatchers.IO) {
//        try {
//            val existingUser = userAccountDao.getUserAccountByEmailAddress(emailAddress)
//
//            if (existingUser == null) {
//                val newUserAccount = UserAccount(
//                    emailAddress = emailAddress,
//                    password = password, // Make sure to hash the password here
//                    firstName = firstName,
//                    lastName = lastName
//                )
//                userAccountDao.insertUserAccount(newUserAccount)
//                SignUpResult.SUCCESS // Enum representing successful sign-up
//            } else {
//                SignUpResult.EXISTING_EMAIL // Enum representing existing email
//            }
//        } catch (e: Exception) {
//            // Log the exception or handle it as needed
//            SignUpResult.UNKNOWN_ERROR // Enum representing an unknown error
//        }
//    }
//}
