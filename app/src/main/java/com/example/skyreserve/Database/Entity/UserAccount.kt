package com.example.skyreserve.Database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccount(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val username: String,

    val password: String,

    val firstName: String,

    val lastName: String,

    // Other properties

    // once all properties are created make constructor
    // no need for getters and setters - because variables are accessible already through property accessor
) {
    // Default constructor
    constructor() : this(0, "", "", "", "")

    // Constructor without 'id' (useful when inserting a new UserAccount)
    constructor(username: String, password: String, firstName: String, lastName: String) :
            this(0, username, password, firstName, lastName)
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