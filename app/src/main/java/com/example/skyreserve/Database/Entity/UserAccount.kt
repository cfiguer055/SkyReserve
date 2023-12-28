package com.example.skyreserve.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccount(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Long = 0,

    @ColumnInfo(name = "email_address")
    val emailAddress: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "state_code")
    val stateCode: String,
    @ColumnInfo(name = "country_code")
    val countryCode: String,
    @ColumnInfo(name = "passport")
    val passport: String,

    // Other properties

    // once all properties are created make constructor
    // no need for getters and setters - because variables are accessible already through property accessor
) {
    // Default constructor
    constructor() : this(
        emailAddress = "",
        password = "",
        firstName = "",
        lastName = "",
        gender = "",
        phone = "",
        dateOfBirth = "",
        address = "",
        stateCode = "",
        countryCode = "",
        passport = ""
    )

    // Constructor without 'id' (useful when inserting a new UserAccount)
    constructor(
        emailAddress: String,
        password: String,
    ) : this(
        emailAddress = emailAddress,
        password = password,
        firstName = "",
        lastName = "",
        gender = "",
        phone = "",
        dateOfBirth = "",
        address = "",
        stateCode = "",
        countryCode = "",
        passport = ""
    )
}

/*
Here's how you might handle password storage:

Hashing: Hash the password using a strong hashing algorithm like bcrypt, which is designed to be
slow and computationally intensive, making it difficult for attackers to brute-force passwords.

Salting: Generate a unique salt for each user and combine it with their password before hashing.
Salting adds an additional layer of security and prevents attackers from using precomputed tables
(rainbow tables) to crack passwords.

Store Hash and Salt: Store the generated hash and the corresponding salt in the database.
 */