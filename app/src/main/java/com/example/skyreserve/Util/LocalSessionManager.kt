package com.example.skyreserve.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*
import javax.inject.Singleton


@Singleton
class LocalSessionManager(private val prefs: SharedPreferences) {
    /**
     * Primary constructor to initialize LocalSessionManager in testing (To not mock
     * Encryption SharedPreferences)
     * Secondary constructor to initialize LocalSessionManager using Context.
     * This maintains backward compatibility with existing code that passes Context.
     *
     * Initializes the SharedPreferences using EncryptedSharedPreferences.
     *
     * @param context the context used to create EncryptedSharedPreferences
     */
    constructor(context: Context) : this(
        EncryptedSharedPreferences.create(
            "AppSessionPrefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    )

    // Creates a login session by storing user email and token in SharedPreferences
    fun createLoginSession(userEmail: String, token: String) {
        val editor = prefs.edit()
        editor.putString("user_id", userEmail)
        editor.putString("session_token", token)
        // Set a new expiry time, e.g., 24 hours from now
        editor.putLong("token_expiry", System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        editor.apply()
    }

    // logout user by removing user-related data from SharedPreferences
    fun logoutUser() {
        val editor = prefs.edit()
        editor.remove("user_email")
        editor.remove("session_token")
        editor.remove("token_expiry")
        editor.apply()
    }

    // Checks if the current token is valid
    fun isTokenValid(): Boolean {
        val token = getUserToken()
        val tokenExpiry = getTokenExpiry()
        return token != null && tokenExpiry > System.currentTimeMillis()
    }

    // Renews the token
    fun renewToken() {
        val userEmail = getUserEmail() ?: return
        val newToken = UUID.randomUUID().toString()
        createLoginSession(userEmail, newToken)
    }

    // Retrieves the session token from SharedPreferences
    private fun getUserToken(): String? {
        return prefs.getString("session_token", null)
    }

    // Retrieves the token expiry time from SharedPreferences
    private fun getTokenExpiry(): Long {
        return prefs.getLong("token_expiry", -1)
    }

    // Retrieves the user email from SharedPreferences
    public fun getUserEmail(): String? {
        return prefs.getString("user_id", null)
    }
}




//class LocalSessionManager(context: Context) {
//    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//
//    //private val prefs: SharedPreferences = context.getSharedPreferences("AppSessionPrefs", Context.MODE_PRIVATE)
//    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
//        "AppSessionPrefs",
//        masterKeyAlias,
//        context,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
