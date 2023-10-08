package com.example.skyreserve.zzzzidk

class Reservation {

    var departureFlight: Flight? = null
        get() = field
        set(value) {
            field = value
        }

    var returnFlight: Flight? = null
        get() = field
        set(value) {
            field = value
        }

    var departureParty: ArrayList<Passenger> = ArrayList()
        get() = field
        set(value) {
            field = value
        }

    var returnParty: ArrayList<Passenger> = ArrayList()
        get() = field
        set(value) {
            field = value
        }

    var reservationID: Int = 0
        get() = field
        set(value) {
            field = value
        }

    var flightPrice: Double = 0.0
        get() = field
        set(value) {
            field = value
        }

    var flightSeatFee: Double = 0.0
        get() = field
        set(value) {
            field = value
        }

    var flightBaggageFee: Double = 0.0
        get() = field
        set(value) {
            field = value
        }

    var flightTax: Double = 0.0
        get() = field
        set(value) {
            field = value
        }

    var flightTotal: Double = 0.0
        get() = field
        set(value) {
            field = value
        }

    var departureDate: String? = null
        get() = field
        set(value) {
            field = value
        }

    var returnDate: String? = null
        get() = field
        set(value) {
            field = value
        }

    var roundTrip: Boolean = false
        get() = field
        set(value) {
            field = value
        }

    var departureFlightNumber: String? = null
        get() = field
        set(value) {
            field = value
        }

    var returnFlightNumber: String? = null
        get() = field
        set(value) {
            field = value
        }

    var departurePartySize: Int = 0
        get() = field
        set(value) {
            field = value
        }

    var returnPartySize: Int = 0
        get() = field
        set(value) {
            field = value
        }


    // one way trip
    constructor(
        reservationID: Int,
        flightPrice: Double,
        flightSeatFee: Double,
        flightBaggageFee: Double,
        flightTax: Double,
        flightTotal: Double,
        departureDate: String,
        departureFlightNumber: String,
        departurePartySize: Int,
        departureParty: ArrayList<Passenger>
    ) {
        this.reservationID = reservationID
        this.flightPrice = flightPrice
        this.flightSeatFee = flightSeatFee
        this.flightBaggageFee = flightBaggageFee
        this.flightTax = flightTax
        this.flightTotal = flightTotal
        this.departureDate = departureDate
        this.returnDate = null
        this.roundTrip = false
        this.departureFlightNumber = departureFlightNumber
        this.returnFlightNumber = null
        this.departurePartySize = departurePartySize
        this.returnPartySize = 0
        this.departureParty = departureParty
    }


    // round trip
    constructor(
        reservationID: Int,
        flightPrice: Double,
        flightSeatFee: Double,
        flightBaggageFee: Double,
        flightTax: Double,
        flightTotal: Double,
        departureDate: String?,
        returnDate: String?,
        departureFlightNumber: String?,
        returnFlightNumber: String?,
        departurePartySize: Int,
        returnPartySize: Int,
        departureParty: ArrayList<Passenger>,
        returnParty: ArrayList<Passenger>
    ) {
        this.reservationID = reservationID
        this.flightPrice = flightPrice
        this.flightSeatFee = flightSeatFee
        this.flightBaggageFee = flightBaggageFee
        this.flightTax = flightTax
        this.flightTotal = flightTotal
        this.departureDate = departureDate
        this.returnDate = returnDate
        this.roundTrip = true
        this.departureFlightNumber = departureFlightNumber
        this.returnFlightNumber = returnFlightNumber
        this.departurePartySize = departurePartySize
        this.returnPartySize = returnPartySize
        this.departureParty = departureParty
        this.returnParty = returnParty
    }


}