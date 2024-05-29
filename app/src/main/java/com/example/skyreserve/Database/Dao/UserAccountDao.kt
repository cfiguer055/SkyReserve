package com.example.skyreserve.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skyreserve.Database.DataTransferObject.UserReservations
import com.example.skyreserve.Database.Entity.UserAccount
import kotlinx.coroutines.flow.Flow


@Dao
interface UserAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccount(userAccount: UserAccount)

    @Update
    suspend fun updateUserAccount(userAccount: UserAccount)

    @Delete
    suspend fun deleteUserAccount(userAccount: UserAccount)


    @Transaction
    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
    fun getUserReservations(userId: Long): LiveData<UserReservations>

    @Query("SELECT * FROM user_accounts")
    fun getAllUsersFlow(): Flow<List<UserAccount>>

//    @Query("SELECT * FROM user_accounts WHERE user_id = :userId")
//    suspend fun getUserAccountById(userId: Long): UserAccount?

    @Query("SELECT * FROM user_accounts WHERE email_address = :emailAddress")
    suspend fun getUserAccountByEmailAddress(emailAddress: String): UserAccount?


    // Add more queries as needed for your app's requirements
}