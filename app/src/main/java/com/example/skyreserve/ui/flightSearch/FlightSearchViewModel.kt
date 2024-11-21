package com.example.skyreserve.ui.flightSearch

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.model.FlightInfo
import com.example.skyreserve.repository.FlightSearchRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.*


class FlightSearchViewModel constructor(private val flightSearchRepository: FlightSearchRepository) : ViewModel() {
    private val _isFormFilled = MutableLiveData<Boolean>()
    val isFormFilled: LiveData<Boolean> get() = _isFormFilled

    private val _flightResults = MutableLiveData<List<FlightInfo>?>()
    val flightResults: MutableLiveData<List<FlightInfo>?> get() = _flightResults

    private val _filteredFlightResults = MutableLiveData<List<FlightInfo>?>()
    val filteredFlightResults: MutableLiveData<List<FlightInfo>?> get() = _filteredFlightResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage


    /**
     * Fetches airport departures and updates the flight results.
     *
     * @param departAirportCode The IATA code of the departure airport (e.g., "LAX").
     */
    fun getAirportDepartures(departAirportCode: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = flightSearchRepository.getAirportDepartures(departAirportCode)
                if (response.isSuccess && response.data != null) {
                    // Map API flights to domain model
                    val flightInfoList = response.data.scheduledDepartures?.let {
                        flightSearchRepository.mapFlightsToFlightInfo(
                            it
                        )
                    }
                    _flightResults.value = flightInfoList
                    _filteredFlightResults.value = flightInfoList // Initialize with all flights

                    // Log details for each scheduled departure flight
                    if (flightInfoList != null) {
                        logEachScheduledDepartureFlight(flightInfoList)
                    }
                } else {
                    _errorMessage.value = response.errorMessage ?: "Unknown error occurred."
                    Log.e(TAG, "Error fetching departures: ${response.errorMessage}")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception in getAirportDepartures", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    private fun logEachScheduledDepartureFlight(flightInfoList: List<FlightInfo>) {
        Log.d(TAG, "Logging ${flightInfoList.size} scheduled departure flights:")
        flightInfoList.forEachIndexed { index, flightInfo ->
            Log.d(
                TAG,
                """
                Flight ${index + 1}:
                    Departure Time: ${flightInfo.departureTime}
                    Arrival Time: ${flightInfo.arrivalTime}
                    Departure City: ${flightInfo.departureCity} (${flightInfo.departureAirportCode})
                    Arrival City: ${flightInfo.arrivalCity} (${flightInfo.arrivalAirportCode})
                    Duration: ${flightInfo.duration}
                    Airline: ${flightInfo.airline}
                    Price: ${flightInfo.price}
                    Trip Type: ${flightInfo.tripType}
                """.trimIndent()
            )
        }
    }
//    fun getAirportDepartures(departAirportCode: String) {
//        _isLoading.value = true
//        _errorMessage.value = null
//
//        viewModelScope.launch {
//            try {
//                val flights = flightSearchRepository.getAirportDepartures(departAirportCode)
//                Log.d(TAG, "Flight Results: $flights")
//                // _flightResults.value = flights
//            } catch (e: Exception) {
//                _errorMessage.value = e.message
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    /**
     * Filters the flight results based on the destination airport code.
     *
     * @param destinationAirportCode The IATA code of the destination airport (e.g., "DEN").
     */
    fun filterFlightsByDestination(destinationAirportCode: String) {
        val currentFlights = _flightResults.value
        if (currentFlights != null) {
            val filteredList = currentFlights.filter { it.arrivalAirportCode.equals(destinationAirportCode, ignoreCase = true) }
            _filteredFlightResults.value = filteredList
            Log.d(TAG, "Filtered flights count: ${filteredList.size}")
        }
    }

    /**
     * Resets the flight filter to show all flights.
     */
    fun resetFlightFilter() {
        _filteredFlightResults.value = _flightResults.value
        Log.d(TAG, "Flight filter reset. Total flights: ${_flightResults.value?.size ?: 0}")
    }








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

