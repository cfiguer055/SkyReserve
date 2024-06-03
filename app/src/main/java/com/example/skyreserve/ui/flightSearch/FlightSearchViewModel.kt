package com.example.skyreserve.ui.flightSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skyreserve.model.FlightInfo


class FlightSearchViewModel constructor() : ViewModel() {
    private val _isFormFilled = MutableLiveData<Boolean>()
    val isFormFilled: LiveData<Boolean> get() = _isFormFilled

    private val _flightResults = MutableLiveData<List<FlightInfo>>()
    val flightResults: LiveData<List<FlightInfo>> = _flightResults

    fun checkFormFilled(
        departAirport: String,
        arriveAirport: String,
        departDate: String,
        returnDate: String,
        passengerCount: Int,
        tripTypeSelected: Boolean
    ) {
        _isFormFilled.value = departAirport.isNotEmpty() &&
                arriveAirport.isNotEmpty() &&
                departDate.isNotEmpty() &&
                returnDate.isNotEmpty() &&
                passengerCount > 0 &&
                tripTypeSelected
    }

    fun isFormValid(
        departAirport: String,
        arriveAirport: String,
        departDate: String,
        returnDate: String,
        passengerCount: Int,
        tripTypeSelected: Boolean
    ): Boolean {
        return departAirport.isNotEmpty() &&
                arriveAirport.isNotEmpty() &&
                departDate.isNotEmpty() &&
                returnDate.isNotEmpty() &&
                passengerCount > 0 &&
                tripTypeSelected
    }

    // Function to load flight results
    fun loadFlightResults(departAirportCode: String, arriveAirportCode: String, departCity: String, arriveCity: String, roundTrip: String) {
        // Your logic to create the flight list
        val flightList = listOf(
            FlightInfo("08:00", "10:00", departCity , arriveCity, departAirportCode, arriveAirportCode,"7h", "Spirit Airlines", "$500", roundTrip),
            FlightInfo("08:15", "14:45", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 30m", "Spirit Airlines", "$500", roundTrip),
            FlightInfo("09:30", "15:00", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 30m", "Delta", "$450", roundTrip),
            FlightInfo("10:45", "17:15", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 30m", "United", "$470", roundTrip),
            FlightInfo("11:20", "17:05", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "Southwest", "$530", roundTrip),
            FlightInfo("12:50", "19:35", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "American Airlines", "$550", roundTrip),
            FlightInfo("13:10", "18:55", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "JetBlue", "$520", roundTrip),
            FlightInfo("14:00", "20:15", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 15m", "Alaska Airlines", "$510", roundTrip),
            FlightInfo("15:35", "22:20", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "Frontier", "$435", roundTrip),
            FlightInfo("16:05", "22:50", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "Spirit", "$499", roundTrip),
            FlightInfo("17:40", "23:25", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "Delta", "$460", roundTrip),
            FlightInfo("18:25", "00:10", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "United", "$480", roundTrip),
            FlightInfo("19:15", "01:00", departCity, arriveCity, departAirportCode, arriveAirportCode, "5h 45m", "Southwest", "$540", roundTrip),
            FlightInfo("20:30", "03:15", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "American Airlines", "$560", roundTrip),
            FlightInfo("21:45", "04:30", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "JetBlue", "$525", roundTrip),
            FlightInfo("22:50", "05:35", departCity, arriveCity, departAirportCode, arriveAirportCode, "6h 45m", "Alaska Airlines", "$515", roundTrip)
        )

        // Post the flight list to the LiveData
        _flightResults.postValue(flightList)
    }
}

