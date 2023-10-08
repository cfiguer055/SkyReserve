package com.example.skyreserve.zzzzidk

class Account {

    companion object {
        private val loginAccount = HashMap<String, Account>()

        fun accountExists(username: String): Boolean {
            return loginAccount.containsKey(username)
        }

        fun registerOrUpdate(account: Account) {
            val username = account.username ?: throw IllegalArgumentException("Username cannot be null")
            loginAccount[username] = account
        }
    }


    var reservationList: ArrayList<Reservation> = ArrayList()
        get() = field
        set(value) {
            field = value
        }

    var payment: Payment? = null
        get() = field
        set(value) {
            field = value
        }

    var reservation: Reservation? = null
        get() = field
        set(value) {
            field = value
        }

    var username: String? = null
        get() = field
        set(value) {
            field = value
        }

    var password: String? = null
        get() = field
        set(value) {
            field = value
        }

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

    var address: String? = null
        get() = field
        set(value) {
            field = value
        }

    var emailAddress: String? = null
        get() = field
        set(value) {
            field = value
        }

    var phoneNumber: String? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(
        reservationList: ArrayList<Reservation>,
        payment: Payment?,
        reservation: Reservation?,
        username: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
        address: String?,
        emailAddress: String?,
        phoneNumber: String?
    ) {
        this.reservationList = reservationList
        this.payment = payment
        this.reservation = reservation
        this.username = username
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.address = address
        this.emailAddress = emailAddress
        this.phoneNumber = phoneNumber
    }

    constructor() : this(
        ArrayList(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    ) {
        // Secondary constructor with default values
    }


    fun addReservationToAccount(reservation: Reservation) {
        reservationList.add(reservation)
    }

}
