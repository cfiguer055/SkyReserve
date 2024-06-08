package com.example.skyreserve.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skyreserve.WhatToDo.DataTransferObject.UserReservations
import com.example.skyreserve.database.room.entity.UserAccount
import kotlinx.coroutines.flow.Flow


@Dao
interface UserAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccount(userAccount: UserAccount)

    @Update
    suspend fun updateUserAccount(userAccount: UserAccount)

    @Delete
    suspend fun deleteUserAccount(userAccount: UserAccount)


    // CHANGE TO FLOW FOR RESERVATION REPO AND DAO
    @Transaction
    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
    fun getUserReservations(userId: Long): LiveData<UserReservations>

    @Query("SELECT * FROM user_accounts")
    fun getAllUsersFlow(): Flow<List<UserAccount>>

//    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
//    suspend fun getUserAccountById(userId: Long): UserAccount?

//    @Query("SELECT * FROM user_accounts WHERE email_address = :emailAddress")
//    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount?

    @Query("SELECT * FROM user_accounts WHERE email_address = :emailAddress")
    fun getUserAccountByEmailAddress(emailAddress: String): Flow<UserAccount?>



    // Add more queries as needed for your app's requirements
}