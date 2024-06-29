//[app](../../../index.md)/[com.example.skyreserve.database.room.dao](../index.md)/[ReservationDao](index.md)

# ReservationDao

[androidJvm]\
interface [ReservationDao](index.md)

## Functions

| Name | Summary |
|---|---|
| [deleteReservation](delete-reservation.md) | [androidJvm]<br>abstract suspend fun [deleteReservation](delete-reservation.md)(reservation: [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)) |
| [getAllReservationsFlow](get-all-reservations-flow.md) | [androidJvm]<br>abstract fun [getAllReservationsFlow](get-all-reservations-flow.md)(): <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"kotlinx.coroutines.flow","classNames":"Flow","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Flow<!--- --->&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)&gt;&gt; |
| [getDepartureBasePrice](get-departure-base-price.md) | [androidJvm]<br>abstract fun [getDepartureBasePrice](get-departure-base-price.md)(reservationId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)&gt; |
| [getReservationById](get-reservation-by-id.md) | [androidJvm]<br>abstract suspend fun [getReservationById](get-reservation-by-id.md)(reservationId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)? |
| [getReservationWithDetails](get-reservation-with-details.md) | [androidJvm]<br>abstract fun [getReservationWithDetails](get-reservation-with-details.md)(reservationId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ReservationDetail](../../com.example.skyreserve.WhatToDo.DataTransferObject/-reservation-detail/index.md)&gt; |
| [getReturnBasePrice](get-return-base-price.md) | [androidJvm]<br>abstract fun [getReturnBasePrice](get-return-base-price.md)(reservationId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)&gt; |
| [insertReservation](insert-reservation.md) | [androidJvm]<br>abstract suspend fun [insertReservation](insert-reservation.md)(reservation: [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)) |
| [updateReservation](update-reservation.md) | [androidJvm]<br>abstract suspend fun [updateReservation](update-reservation.md)(reservation: [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)) |
