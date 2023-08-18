package com.example.skyreserve.Entities

class Baggage {

    var passengerName: String? = null
        get() = field
        set(value) {
            field = value
        }

    var numBags: Int = 0
        get() = field
        set(value) {
            field = value
        }


    constructor(
        passengerName: String?,
        numBags: Int
    ) {
        this.passengerName = passengerName
        this.numBags = numBags
    }

}