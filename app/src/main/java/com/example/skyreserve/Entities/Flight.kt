package com.example.skyreserve.Entities

class Flight {

    var departureAirport: Airport? = null
        get() = field
        set(value) {
            field = value
        }

    var arrivalAirport: Airport? = null
        get() = field
        set(value) {
            field = value
        }

    var airline: Airline? = null
        get() = field
        set(value) {
            field = value
        }

    var baggage: ArrayList<Baggage> = ArrayList()
        get() = field
        set(value) {
            field = value
        }

    var seats: Seats? = null
        get() = field
        set(value) {
            field = value
        }

    var flightID: String? = null
        get() = field
        set(value) {
            field = value
        }
    var departureDate: String? = null
        get() = field
        set(value) {
            field = value
        }
    var arrivalDate: String? = null
        get() = field
        set(value) {
            field = value
        }
    var departureTime: String? = null
        get() = field
        set(value) {
            field = value
        }
    var arrivalTime: String? = null
        get() = field
        set(value) {
            field = value
        }
    var departureLocation: String? = null
        get() = field
        set(value) {
            field = value
        }
    var arrivalLocation: String? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(
        flightID: String?,
        departureDate: String?,
        arrivalDate: String?,
        departureTime: String?,
        arrivalTime: String?,
        departureLocation: String?,
        arrivalLocation: String?,
    ) {
        this.flightID = flightID
        this.departureDate = departureDate
        this.arrivalDate = arrivalDate
        this.departureTime = departureTime
        this.arrivalTime = arrivalTime
        this.departureLocation = departureLocation
        this.arrivalLocation = arrivalLocation
    }
}