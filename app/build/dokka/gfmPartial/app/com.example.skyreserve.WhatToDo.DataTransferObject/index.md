//[app](../../index.md)/[com.example.skyreserve.WhatToDo.DataTransferObject](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ReservationDetail](-reservation-detail/index.md) | [androidJvm]<br>data class [ReservationDetail](-reservation-detail/index.md)(val reservation: [Reservation](../com.example.skyreserve.database.room.entity/-reservation/index.md), val flightPrice: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BaseFlightPrice](../com.example.skyreserve.database.room.entity/-base-flight-price/index.md)&gt;, val taxes: [Taxes](../com.example.skyreserve.database.room.entity/-taxes/index.md)) |
| [UserReservations](-user-reservations/index.md) | [androidJvm]<br>data class [UserReservations](-user-reservations/index.md)(val userAccount: [UserAccount](../com.example.skyreserve.database.room.entity/-user-account/index.md), val reservations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ReservationDetail](-reservation-detail/index.md)&gt;) |
