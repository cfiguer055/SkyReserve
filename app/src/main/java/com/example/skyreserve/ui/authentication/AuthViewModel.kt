package com.example.skyreserve.ui.authentication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.util.LocalSessionManager
import com.example.skyreserve.util.SignInResult
import com.example.skyreserve.util.SignUpResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import androidx.core.util.PatternsCompat
import kotlinx.coroutines.flow.first


/**
 * EN:
 * ViewModel responsible for managing authentication-related actions, including signing in,
 * signing up, session validation, and logout. Uses StateFlow and LiveData to track and communicate
 * the current authentication status and user details.
 *
 * ES:
 * ViewModel responsable de gestionar las acciónes relacionados con la autenticación, incluyendo
 * iniciar sesión, registrarse, validación de sesión, y cierre de sesión. Utiliza StateFlow y LiveData
 * para rastrear y comunicar el estado actual de autenticación y los detalles del usuario.
 *
 * IT:
 *
 *
 * */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository, // Handles authentication logic
    private val sessionManager: LocalSessionManager, // Manages local session state
    @ApplicationContext private val context: Context, // Added for network check
) : ViewModel() {


    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    // LiveData or StateFlow to hold the sign-up and sign-in result
    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> get() = _signUpResult

    private val _signInResult = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> get() = _signInResult

    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser: StateFlow<UserAccount?> = _currentUser


    /**
     * EN:
     * Initiates the sign-in process. Validates network availability and checks credentials
     * before logging in and creating a session.
     *
     * ES:
     * Inicia el proceso de inicio de sesión. Valida la disponibilidad de red y verifica las
     * credenciales antes de iniciar sesión y crear una sesión.
     *
     * IT:
     *
     *
     * */
    fun signIn(emailAddress: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.signIn(emailAddress, password).collect { signInSuccess ->
                    when {
                        !isNetworkAvailable() -> {
                            _signInResult.postValue(SignInResult.NETWORK_ERROR)
                        }
                        signInSuccess -> {
                            _signInResult.postValue(SignInResult.SUCCESS)
                            authRepository.getUserAccountByEmailAddress(emailAddress)
                                .collect { userAccount ->
                                    _currentUser.value = userAccount
                                    userAccount?.let {
                                        val token = UUID.randomUUID().toString()
                                        sessionManager.createLoginSession(it.emailAddress, token)
                                        _isLoggedIn.value = true
                                    }
                                }
                        }
                        else -> {
                            _signInResult.postValue(SignInResult.INVALID_CREDENTIALS)
                            _isLoggedIn.value = false
                            _currentUser.value = null
                        }
                    }
                }
            } catch (e: Exception) {
                // Post an unknown error result when an exception occurs
                _signInResult.postValue(SignInResult.UNKNOWN_ERROR)
                _isLoggedIn.value = false
                _currentUser.value = null
            }
        }
    }


    /**
     * EN:
     * Initiates the sign-up process. Validates email format, password strength,
     * network availability, and whether the email is already registered.
     *
     * ES:
     * Inicializa el proceso de registro. Valida el formato de correo electrónico, la fortaleza
     * de la contraseña, la disponibilidad de red y si el correo electrónico ya está registrado.
     *
     * IT:
     *
     *
     * */
    fun signUp(emailAddress: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            when {
                emailAddress.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                    _signUpResult.postValue(SignUpResult.MISSING_INFORMATION)
                }
                !isValidEmail(emailAddress) -> {
                    _signUpResult.postValue(SignUpResult.INVALID_EMAIL)
                }
                password.length < 8 -> {
                    _signUpResult.postValue(SignUpResult.SHORT_PASSWORD)
                }
                !password.matches(Regex(".*[A-Z].*")) -> {
                    _signUpResult.postValue(SignUpResult.MISSING_CAPITAL_LETTER)
                }
                !password.matches(Regex(".*[a-z].*")) -> {
                    _signUpResult.postValue(SignUpResult.MISSING_LOWCASE_LETTER)
                }
                !password.matches(Regex(".*\\d.*")) -> {
                    _signUpResult.postValue(SignUpResult.MISSING_DIGIT)
                }
                password != confirmPassword -> {
                    _signUpResult.postValue(SignUpResult.PASSWORD_NO_MATCH)
                }
                !isNetworkAvailable() -> {
                    _signUpResult.postValue(SignUpResult.NETWORK_ERROR)
                }
                else -> {
                    authRepository.isEmailExisting(emailAddress).collect { emailExists ->
                        if (emailExists) {
                            _signUpResult.postValue(SignUpResult.EXISTING_EMAIL)
                        } else {
                            authRepository.signUp(emailAddress, password).collect { success ->
                                if (success) {
                                    _signUpResult.postValue(SignUpResult.SUCCESS)
                                    val token = UUID.randomUUID().toString()
                                    sessionManager.createLoginSession(emailAddress, token)
                                    _currentUser.value = UserAccount(emailAddress = emailAddress, password = password) // Simplified version
                                    _isLoggedIn.value = true
                                } else {
                                    _signUpResult.postValue(SignUpResult.UNKNOWN_ERROR)
                                    _isLoggedIn.value = false
                                    _currentUser.value = null
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * EN: Validates if the current session token is still valid.
     *
     * ES: Valida si el token de sesión actual sigue siendo válido.
     *
     * IT:
     *
     * */
    fun validateSession() : Boolean {
        return sessionManager.isTokenValid()
    }

    /**
     * EN: Refreshes the session token to keep the session active.
     *
     * ES: Actualiza el token de sesión para mantener la sesión activa.
     *
     * IT:
     *
     * */
    fun refreshSessionToken() {
        sessionManager.renewToken()
    }


    /**
     * EN: Logs out the user by clearing the session and resetting the user state.
     *
     * ES: Cierra la sesión del usuario limpiando la sesión y restableciendo el estado del usuario.
     *
     * IT:
     *
     * */
    fun logout() {
        sessionManager.logoutUser()
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    /**
     * EN: Checks if there is an active network connection available.
     *
     * ES: Verifica si hay una conexión de red activa disponible.
     *
     * IT:
     *
     * */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * EN: Checks if the given email is already registered in the system.
     *
     * ES: Verifica si el correo electrónico dado ya está registrado en el sistema.
     *
     * IT:
     *
     * */
    private suspend fun isEmailExisting(email: String): Boolean {
        return authRepository.isEmailExisting(email).first()
    }
    // Additional ViewModel logic...

    /**
     * EN: Validates if the provided email format is correct.
     *
     * ES: Valida si el formatto del correo electrónico proporcionado es correcto.
     *
     * IT:
     *
     * */
    private fun isValidEmail(email: String): Boolean {

        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}

