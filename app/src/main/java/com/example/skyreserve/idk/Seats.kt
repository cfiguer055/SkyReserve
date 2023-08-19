package com.example.skyreserve.idk

class Seats {

    var reservedSeatNumbers: ArrayList<String> = ArrayList()
        get() = field
        set(value) {
            field = value
        }

    var seatClasses: ArrayList<String> = ArrayList()
        get() = field
        set(value) {
            field = value
        }


    constructor(
        reservedSeatNumbers: ArrayList<String>,
        seatClasses: ArrayList<String>
    ) {
        this.reservedSeatNumbers = reservedSeatNumbers;
        this.seatClasses = seatClasses;
    }

}