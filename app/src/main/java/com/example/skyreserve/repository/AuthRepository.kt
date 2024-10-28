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



/**
 * EN:
 * Repository for handling user authentication and registration by interacting with the local
 * database. This class will later transition to making network requests to a backend server
 * for user authentication, registration, and token validation.
 *
 * ES:
 * Repositorio para gestionar la autenticación y el registro de usuarios interactuando con
 * la base de datos local. Esta clase luego se tranformará para realizar solicutudes de red a un
 * servidor backend para autenticación de usuario, registro y validación de tokens.
 *
 * IT:
 *
 *
 * */
@Singleton
class AuthRepository @Inject constructor(private val userAccountDao: UserAccountDao) {

    /**
     * EN:
     * Attempts to sign in by checking if the provided email and password match a user account
     * in the local database. In the future, this will make a network request to the backend for
     * validation.
     *
     * ES:
     * Intenta iniciar sesión comprobando si el correo electrónico y la contraseña proporcionados
     * coinciden con una cuenta de usuario en la base de datos local. En el futuro, se realizará
     * una solicitud de red al backend para validación.
     *
     * IT:
     *
     *
     * */
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

    /**
     * EN:
     * Registers a new user by hashing the password and storing it along with the email in
     * the local database. This will later be replaced by a network request to the backend
     * for user registration.
     *
     * ES:
     * Registra un nuevo usuario cifrando la contraseña y almacenándola junto con el correo electrónico
     * en la base de datos local. Esto se reemplazará posteriormente con una solicitud de red al backend
     * para el registro de usuarios.
     *
     * IT:
     *
     *
     * */
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

    /**
     * EN:
     * Checks if an email address already exists in the local database, used for avoiding duplicate
     * accounts during registration. This check will eventually be replaced by a backend request.
     *
     * ES:
     * Verifica si una dirección de correo electrónico ya existe en la base de datos local, utilizado
     * para evitar cuentas duplicandas durante el registro. Esta verifición se reemplazará eventualmente
     * por una solicitud al backend.
     *
     * IT:
     *
     *
     * */
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

    /**
     * EN:
     * Retrieves a user account by email address from the local database, returning null if
     * not found. This method will later retrieve the user data from the backend.
     *
     * ES:
     * Recupera una cuenta de usuario por dirección de correo electrónico de la base de datos local,
     * devolviendo null si no se encuentra. Este método recuperará posteriorment los datos del
     * usuario desde el backend.
     *
     * IT:
     *
     *
     * */
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

    /**
     * EN:
     * Hashes a plain text password using BCrypt for secure storage in the database.
     *
     * ES:
     * Cilfra una contraseña en texto plano usando BCrypt para una almacenamiento seguro
     * en la base de datos.
     *
     * IT:
     *
     * */
    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    /**
     * EN:
     * Checks if a plain text password matches a hashed password stored in the database using BCrypt.
     *
     * ES:
     * Verifica si una contraseña en texto plano coincide con una contraseña cilfrada almacenada
     * en la base de datos usando BCrypt.
     *
     * IT:
     *
     *
     * */
    private fun checkPassword(plainPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }
}
