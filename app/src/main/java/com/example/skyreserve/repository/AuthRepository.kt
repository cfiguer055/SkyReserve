package com.example.skyreserve.repository

import android.util.Log
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject
import javax.inject.Singleton

// LATER BACKEND IMPLEMENTATION
// This class will need significant modifications. Instead of directly querying the local database for
// user authentication, it will make network requests to the backend for user sign-in, sign-up, and
// token validation.


@Singleton
class AuthRepository @Inject constructor(private val userAccountDao: UserAccountDao) {

    fun signIn(emailAddress: String, password: String): Flow<Boolean> = flow {
        try {
            val userAccount = withContext(Dispatchers.IO) {
                userAccountDao.getUserAccountByEmailAddress(emailAddress).firstOrNull()
            }
            val isValid = userAccount != null && checkPassword(password, userAccount.password)
            emit(isValid)
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error in signIn", e)
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    fun signUp(emailAddress: String, password: String): Flow<Boolean> = flow {
        try {
            val existingUser = withContext(Dispatchers.IO) {
                userAccountDao.getUserAccountByEmailAddress(emailAddress).firstOrNull()
            }
            if (existingUser == null) {
                val hashedPassword = hashPassword(password)
                val newUserAccount = UserAccount(emailAddress = emailAddress, password = hashedPassword)
                withContext(Dispatchers.IO) {
                    userAccountDao.insertUserAccount(newUserAccount)
                }
                emit(true)
            } else {
                emit(false)
            }
        } catch (e: Exception) {
            //Log.e("AuthRepo", "Error in signUp", e)
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    fun isEmailExisting(email: String): Flow<Boolean> = flow {
        try {
            val userAccount = withContext(Dispatchers.IO) {
                userAccountDao.getUserAccountByEmailAddress(email).firstOrNull()
            }
            emit(userAccount != null)
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error checking email", e)
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    fun getUserAccountByEmailAddress(emailAddress: String): Flow<UserAccount?> = flow {
        try {
            val userAccount = withContext(Dispatchers.IO) {
                userAccountDao.getUserAccountByEmailAddress(emailAddress).firstOrNull()
            }
            emit(userAccount)
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error fetching user account", e)
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    private fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }
}


//@Singleton
//class AuthRepository @Inject constructor(private val userAccountDao: UserAccountDao) {
//
//    fun signIn(emailAddress: String, password: String): Flow<Boolean> = flow {
//        emit(withContext(Dispatchers.IO) {
//            try {
//                val userAccount = userAccountDao.getUserAccountByEmailAddress(emailAddress).firstOrNull()
//                userAccount != null && checkPassword(password, userAccount.password)
//            } catch (e: Exception) {
//                Log.e("AuthRepo", "Error in signIn", e)
//                false
//            }
//        })
//    }.flowOn(Dispatchers.IO)
//
//    fun signUp(emailAddress: String, password: String): Flow<Boolean> = flow {
//        emit(withContext(Dispatchers.IO) {
//            try {
//                val existingUser = userAccountDao.getUserAccountByEmailAddress(emailAddress).firstOrNull()
//                if (existingUser == null) {
//                    val hashedPassword = hashPassword(password)
//                    val newUserAccount = UserAccount(emailAddress = emailAddress, password = hashedPassword)
//                    userAccountDao.insertUserAccount(newUserAccount)
//                    true
//                } else {
//                    false
//                }
//            } catch (e: Exception) {
//                Log.e("AuthRepo", "Error in signUp", e)
//                false
//            }
//        })
//    }.flowOn(Dispatchers.IO)
//
//
//    fun isEmailExisting(email: String): Flow<Boolean> = flow {
//        emit(withContext(Dispatchers.IO) {
//            try {
//                val userAccount = userAccountDao.getUserAccountByEmailAddress(email).firstOrNull()
//                userAccount != null
//            } catch (e: Exception) {
//                Log.e("AuthRepo", "Error checking email", e)
//                false
//            }
//        })
//    }.flowOn(Dispatchers.IO)
//
//    fun getUserAccountByEmailAddress(emailAddress: String): Flow<UserAccount?> = flow {
//        emit(withContext(Dispatchers.IO) {
//            userAccountDao.getUserAccountByEmailAddress(emailAddress).firstOrNull()
//        })
//    }.flowOn(Dispatchers.IO)
//
//    // Helper functions for password hashing and checking
//    private fun hashPassword(password: String): String {
//        return BCrypt.hashpw(password, BCrypt.gensalt())
//    }
//
//    private fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
//        return BCrypt.checkpw(plainPassword, hashedPassword)
//    }
//
//
//    // Additional methods can be added for managing user sessions, tokens, etc.
//}
