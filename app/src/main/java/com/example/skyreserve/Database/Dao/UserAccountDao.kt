package com.example.skyreserve.Database.Dao

import androidx.room.*
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

    @Query("SELECT * FROM user_accounts")
    fun getAllUsersFlow(): Flow<List<UserAccount>>

    @Query("SELECT * FROM user_accounts WHERE id = :id")
    suspend fun getUserAccountById(id: Long): UserAccount?

    @Query("SELECT * FROM user_accounts WHERE username = :username")
    suspend fun getUserAccountByUsername(username: String): UserAccount?

    // Add more queries as needed for your app's requirements
}