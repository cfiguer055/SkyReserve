package com.example.skyreserve.idk

class Passenger {

    var firstName: String? = null
        get() = field
        set(value) {
            field = value
        }

    var lastName: String? = null
        get() = field
        set(value) {
            field = value
        }

    var passportCountry: String? = null
        get() = field
        set(value) {
            field = value
        }

    var gender: String? = null
        get() = field
        set(value) {
            field = value
        }

    var dateOfBirth: String? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(
        firstName: String?,
        lastName: String?,
        passportCountry: String?,
        gender: String?,
        dateOfBirth: String?
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.passportCountry = passportCountry
        this.gender = gender
        this.dateOfBirth = dateOfBirth
    }

}