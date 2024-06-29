//[app](../../../index.md)/[com.example.skyreserve.ui.flightSearch](../index.md)/[FlightSearchViewModel](index.md)

# FlightSearchViewModel

[androidJvm]\
class [FlightSearchViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [FlightSearchViewModel](-flight-search-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [flightResults](flight-results.md) | [androidJvm]<br>val [flightResults](flight-results.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[FlightInfo](../../com.example.skyreserve.model/-flight-info/index.md)&gt;&gt; |
| [isFormFilled](is-form-filled.md) | [androidJvm]<br>val [isFormFilled](is-form-filled.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961) | [androidJvm]<br>open fun [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [checkFormFilled](check-form-filled.md) | [androidJvm]<br>fun [checkFormFilled](check-form-filled.md)(departAirport: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), arriveAirport: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), departDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), returnDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), passengerCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), tripTypeSelected: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [isFormValid](is-form-valid.md) | [androidJvm]<br>fun [isFormValid](is-form-valid.md)(departAirport: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), arriveAirport: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), departDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), returnDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), passengerCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), tripTypeSelected: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [loadFlightResults](load-flight-results.md) | [androidJvm]<br>fun [loadFlightResults](load-flight-results.md)(departAirportCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), arriveAirportCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), departCity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), arriveCity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), roundTrip: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
