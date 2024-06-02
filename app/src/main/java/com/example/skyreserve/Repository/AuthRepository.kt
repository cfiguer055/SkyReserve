package com.example.skyreserve.Repository

import android.util.Log
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Util.EncryptionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

// LATER BACKEND IMPLEMENTATION
// This class will need significant modifications. Instead of directly querying the local database for
// user authentication, it will make network requests to the backend for user sign-in, sign-up, and
// token validation.
@Singleton
class AuthRepository @Inject constructor(private val userAccountDao: UserAccountDao) {

    // Perform user sign-in
    suspend fun signIn(emailAddress: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("AuthRepo","emailAddress: $emailAddress")
                //val encryptedEmail = EncryptionUtil.encrypt(emailAddress)
                //Log.d("AuthRepo","emailAddress: $encryptedEmail")

                // Query the local database to find the user by username and password
                //val userAccount = userAccountDao.getUserAccountByEmailAddress(encryptedEmail)
                val userAccount = userAccountDao.getUserAccountByEmailAddress(emailAddress)

                // Delete this eventually
                Log.d("AuthRepo","userAccount: $userAccount")
                if (userAccount != null) {
                    Log.d("AuthRepo","password: ${userAccount.password}")
                }


                return@withContext userAccount != null && checkPassword(password, userAccount.password)

            } catch (e: Exception) {
                Log.e("AuthRepo", "Error SignIn", e)
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
                    Log.d("AuthRepo","emailAddress: $emailAddress")

                    //val encryptedEmail = EncryptionUtil.encrypt(emailAddress)

                    //Log.d("AuthRepo","emailAddress: $encryptedEmail")

                    // Hash the password before storing it
                    val hashedPassword = hashPassword(password)

                    // Create a new UserAccount entity and insert it into the database
                    val newUserAccount = UserAccount(
                        //emailAddress = encryptedEmail,
                        emailAddress = emailAddress,
                        password = hashedPassword
                    )
                    userAccountDao.insertUserAccount(newUserAccount)
                    return@withContext true // Sign-up successful
                } else {
                    return@withContext false // User with the same username already exists
                }
            } catch (e: Exception) {
                Log.e("AuthRepo", "Error SignUpo", e)
                // Handle exceptions (e.g., database errors)
                return@withContext false
            }
        }
    }

    // Check if an email exists in the database
    suspend fun isEmailExisting(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                //val encryptedEmail = EncryptionUtil.encrypt(email)
                //val userAccount = userAccountDao.getUserAccountByEmailAddress(encryptedEmail)
                val userAccount = userAccountDao.getUserAccountByEmailAddress(email)

                //val userAccount = userAccountDao.getUserAccountByEmailAddress(email)
                return@withContext userAccount != null
            } catch (e: Exception) {
                // Handle exceptions (e.g., database errors)
                return@withContext false
            }
        }
    }

    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount? {
        //val encryptedEmail = EncryptionUtil.encrypt(emailAddress)
        //val userAccount = userAccountDao.getUserAccountByEmailAddress(encryptedEmail)
        val userAccount = userAccountDao.getUserAccountByEmailAddress(emailAddress)
//        userAccount?.apply {
//            this.emailAddress = EncryptionUtil.decrypt(this.emailAddress)
////            this.phone = this.phone?.let { EncryptionUtil.decrypt(it) }
////            this.dateOfBirth = this.dateOfBirth?.let { EncryptionUtil.decrypt(it) }
////            this.address = this.address?.let { EncryptionUtil.decrypt(it) }
////            this.stateCode = this.stateCode?.let { EncryptionUtil.decrypt(it) }
////            this.countryCode = this.countryCode?.let { EncryptionUtil.decrypt(it) }
////            this.passport = this.passport?.let { EncryptionUtil.decrypt(it) }
//        }
        if (userAccount != null) {
            Log.d("AuthRepo", "emailAddress: ${userAccount.emailAddress}")
        }
        return userAccount
    }

    // Helper functions for password hashing and checking
    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    private fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }


    // Additional methods can be added for managing user sessions, tokens, etc.
}
