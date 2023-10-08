package com.example.skyreserve.zzzzidk

class Airport {

    var airportCode: String? = null
        get() = field
        set(value) {
            field = value
        }

    var airportName: String? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(
        airportCode: String?,
        airportName: String?
    ) {
        this.airportCode = airportCode
        this.airportName = airportName
    }

}