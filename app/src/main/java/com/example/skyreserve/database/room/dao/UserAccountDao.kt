package com.example.skyreserve.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skyreserve.WhatToDo.DataTransferObject.UserReservations
import com.example.skyreserve.database.room.entity.UserAccount
import kotlinx.coroutines.flow.Flow


/**
 * EN:
 * Data Access Object (DAO) interface for managing user account-related database operations.
 * Includes functions for inserting, updating, deleting, and querying user accounts and reservations.
 *
 * ES:
 * Interfaz de Objeto de Accesso a Datos para gestionar las operaciones relacionadas con
 * las cuentas de usuario en la base de datos. Incluye funciones para insertar, actualizar, eliminar
 * y consultar cuentas de usuario y reservas.
 *
 * IT:
 *
 *
 * */
@Dao
interface UserAccountDao {

    /**
     * EN:
     * Inserts a new user account into the database, replacing any existing entry with the same ID
     * if a conflict occurs.
     *
     * ES:
     * Inserta una nueva cuenta de usuario en la base de datos, reemplazando cualquier entrada existente
     * con el mismo ID en caso de conflicto.
     *
     * IT:
     *
     *
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccount(userAccount: UserAccount)

    /**
     * EN: Updates an existing user account in the database.
     *
     * ES: Actualiza una cuenta de usuario existente en la base de datos.
     *
     * IT:
     *
     */
    @Update
    suspend fun updateUserAccount(userAccount: UserAccount)

    /**
     * EN: Deletes a specified user account from the database.
     *
     * ES: Elimina una cuenta de usuario especifica de la base de datos.
     *
     * IT:
     *
     * */
    @Delete
    suspend fun deleteUserAccount(userAccount: UserAccount)

    /**
     * EN:
     * Retrieves a user's reservations as a LiveData object. Utilizes a transaction to ensure
     * the query is consistent across related tables.
     *
     * ES:
     * Recupera las reservas de un usuario como un objeto LiveData. Utiliza una transacción para
     * asegurar que la consulta sea consistente en las tablas relacionadas.
     *
     * IT:
     *
     * */
    // CHANGE TO FLOW FOR RESERVATION REPO AND DAO
    @Transaction
    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
    fun getUserReservations(userId: Long): LiveData<UserReservations>

    /**
     * EN:
     * Returns a Flow of all user accounts, emitting updates whenever there are changes in the data.
     *
     * ES:
     * Devuelve un Flow de todos las cuentas de usuario, emitiendo actualizaciones cada vez que haya
     * cambios en los datos.
     *
     * IT:
     *
     *
     * */
    @Query("SELECT * FROM user_accounts")
    fun getAllUsersFlow(): Flow<List<UserAccount>>

//    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
//    suspend fun getUserAccountById(userId: Long): UserAccount?

//    @Query("SELECT * FROM user_accounts WHERE email_address = :emailAddress")
//    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount?

    /**
     * EN:
     * Retrieves a user account by email address as a Flow, emitting updates whenever the data changes.
     *
     * ES:
     * Recupera una cuenta de usuario por dirección de correo electrónico como un Flow, emitiendo
     * actualizaciones cada vez que los datos cambian.
     *
     * IT:
     *
     * */
    @Query("SELECT * FROM user_accounts WHERE email_address = :emailAddress")
    fun getUserAccountByEmailAddress(emailAddress: String): Flow<UserAccount?>



    // Add more queries as needed for your app's requirements
}