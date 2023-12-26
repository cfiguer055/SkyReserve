package com.example.skyreserve.Database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccount(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val emailAddress: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val phone: String,
    val dateOfBirth: String,
    val address: String,
    val stateCode: String,
    val countryCode: String,
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