package com.example.skyreserve.Model

class Airline {

    var airlineID: String? = null
        get() = field
        set(value) {
            field = value
        }

    var airlineName: String? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(
        airlineID: String?,
        airlineName: String?
    ) {
        this.airlineID = airlineID
        this.airlineName = airlineName
    }
}