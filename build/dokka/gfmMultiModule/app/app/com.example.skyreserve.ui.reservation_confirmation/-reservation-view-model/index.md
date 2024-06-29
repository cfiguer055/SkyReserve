//[app](../../../index.md)/[com.example.skyreserve.ui.reservation_confirmation](../index.md)/[ReservationViewModel](index.md)

# ReservationViewModel

[androidJvm]\
class [ReservationViewModel](index.md)(reservationDao: [ReservationDao](../../com.example.skyreserve.database.room.dao/-reservation-dao/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [ReservationViewModel](-reservation-view-model.md) | [androidJvm]<br>constructor(reservationDao: [ReservationDao](../../com.example.skyreserve.database.room.dao/-reservation-dao/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961) | [androidJvm]<br>open fun [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [getReservationWithPrice](get-reservation-with-price.md) | [androidJvm]<br>fun [getReservationWithPrice](get-reservation-with-price.md)(reservationId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ReservationDetail](../../com.example.skyreserve.WhatToDo.DataTransferObject/-reservation-detail/index.md)&gt; |
