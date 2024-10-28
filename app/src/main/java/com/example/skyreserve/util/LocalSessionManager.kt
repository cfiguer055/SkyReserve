package com.example.skyreserve.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*
import javax.inject.Singleton


/**
 * EN:
 * Class responsible for managing the user's session data, including login session creation,
 * token renewal, and session validity checks. Uses EncryptedSharedPreferences for secure
 * data storage.
 *
 * ES:
 * Clase responsable de gestionar los datos de la sesión del usuario, incluyendo la creación de
 * la sesión de inicio, la renovación del token y las verificaciónes de validez de la sesión.
 * Utiliza EncryptedSharedPreferences para el almacenamiento seguro de datos.
 *
 * IT:
 *
 *
 */
@Singleton
class LocalSessionManager(private val prefs: SharedPreferences) {
    /**
     * ENGLISH:
     * 1) Primary constructor to initialize LocalSessionManager in testing (to avoid mocking
     * EncryptedSharedPreferences).
     * 2) The secondary constructor initializes LocalSessionManager
     * using Context, ensuring backward compatibility with existing code.
     * 3) Initializes SharedPreferences securely using EncryptedSharedPreferences or falls
     * back to regular SharedPreferences in case of an error.
     **************************************************************
     * ESPAÑOL:
     * 1) Constructor primario para inicializar LocalSessionManager en pruebas (para evitar la
     * necesidad de simular EncryptedSharedPreferences)
     * 2) El constructor secundario inicializa LocalSessionManager usando Context, garantizando
     * compatibilidad con el código existente.
     * 3) Inicializa SharedPreferences de forma segura usando EncryptedSharedPreferences o, en
     * caso de error, utiliza SharedPreferences regular.
     **************************************************************
     * ITALIANO:
     * 1)
     * 2)
     * 3)
     **************************************************************
     */
    constructor(context: Context) : this(
        try {
            EncryptedSharedPreferences.create(
                "AppSessionPrefs",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            Log.e("LocalSessionManager", "Error creating EncryptedSharedPreferences", e)
            context.getSharedPreferences("AppSessionPrefs", Context.MODE_PRIVATE) // Fallback to regular SharedPreferences
        }
    )

    /**
     * EN:
     * Creates a login session by storing the user's email and a generated session token,
     * along with an expiry timestamp set to 24 hours from the current time.
     *
     * ES:
     * Crea una sesión de inicio de sesión almacenando el correo electrónico del usuario y un
     * token de sesión generado, junto con una marca de tiempo de expiración configurada a 24
     * horas desde el momento actual.
     *
     * IT:
     *
     *
     */
    fun createLoginSession(userEmail: String, token: String) {
        val editor = prefs.edit()
        editor.putString("user_id", userEmail)
        editor.putString("session_token", token)
        // Set a new expiry time, e.g., 24 hours from now
        editor.putLong("token_expiry", System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        editor.apply()
    }

    /**
     * EN: Logs out the user by clearing session-related data from SharedPreferences.
     *
     * ES: Cierra la sesión del usuario eliminando los datos relacionados con SharedPreferences
     *
     * IT:
     *
     */
    fun logoutUser() {
        val editor = prefs.edit()
        editor.remove("user_email")
        editor.remove("session_token")
        editor.remove("token_expiry")
        editor.apply()
    }

    /**
     * EN:
     * Checks if the current session token is valid by verifying its existence and expiry time.
     *
     * ES:
     * Verifica si el token de sesión es válido comprobando su existencia y tiempo de expiración.
     *
     * IT:
     *
     *
     */
    fun isTokenValid(): Boolean {
        val token = getUserToken()
        val tokenExpiry = getTokenExpiry()
        return token != null && tokenExpiry > System.currentTimeMillis()
    }

    /**
     * EN:
     * Renews the session token by generating a new one and creating a new login session
     * while retaining the user’s email.
     *
     * ES:
     * Renueva el token de sesión generando uno nuevo y creando una nueva sesión de inicio
     * manteniendo el correo electrónico del usuario.
     *
     * IT:
     *
     *
     */
    fun renewToken() {
        val userEmail = getUserEmail() ?: return
        val newToken = UUID.randomUUID().toString()
        createLoginSession(userEmail, newToken)
    }

    /**
     * EN: Retrieves the session token from SharedPreferences.
     *
     * ES: Recupera el token de sesión de SharedPreferences.
     *
     * IT:
     *
     */
    private fun getUserToken(): String? {
        return prefs.getString("session_token", null)
    }

    /**
     * EN: Retrieves the token expiry time from SharedPreferences.
     *
     * ES: Recupera el tiempo de expiración del token de SharedPreferences.
     *
     * IT:
     *
     */
    private fun getTokenExpiry(): Long {
        return prefs.getLong("token_expiry", -1)
    }

    /**
     * EN:
     * Retrieves the user email from SharedPreferences.
     *
     * ES:
     * Recupera el correo electrónico del usuario de SharedPreferences.
     *
     * IT:
     * Recupera l'email dell'utente da SharedPreferences.
     */
    public fun getUserEmail(): String? {
        return prefs.getString("user_id", null)
    }
}

