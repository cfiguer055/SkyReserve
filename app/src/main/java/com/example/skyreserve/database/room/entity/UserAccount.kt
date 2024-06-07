package com.example.skyreserve.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skyreserve.Util.UserData


@Entity(tableName = "user_accounts")
data class UserAccount(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Long = 0,

    @ColumnInfo(name = "email_address")
    var emailAddress: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "first_name")
    var firstName: String? = null,

    @ColumnInfo(name = "last_name")
    var lastName: String? = null,

    @ColumnInfo(name = "gender")
    var gender: String? = null,

    @ColumnInfo(name = "phone")
    var phone: String? = null,

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "state_code")
    var stateCode: String? = null,

    @ColumnInfo(name = "country_code")
    var countryCode: String? = null,

    @ColumnInfo(name = "passport")
    var passport: String? = null

    // Other properties if any

) {
    constructor(email: String, password: String) : this(
        emailAddress = email,
        password = password
    )

    constructor(email: String, password: String, userDetails: UserData) : this(
        emailAddress = email,
        password = password,
        firstName = userDetails.firstName,
        lastName = userDetails.lastName,
        gender = userDetails.gender,
        phone = userDetails.phone,
        dateOfBirth = userDetails.dateOfBirth,
        address = userDetails.address,
        stateCode = userDetails.stateCode,
        countryCode = userDetails.countryCode,
        passport = userDetails.passport
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