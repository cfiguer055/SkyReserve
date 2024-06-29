//[app](../../../index.md)/[com.example.skyreserve.repository](../index.md)/[ReservationRepository](index.md)

# ReservationRepository

[androidJvm]\
@Singleton

class [ReservationRepository](index.md)@Injectconstructor(reservationDao: [ReservationDao](../../com.example.skyreserve.database.room.dao/-reservation-dao/index.md))

## Constructors

| | |
|---|---|
| [ReservationRepository](-reservation-repository.md) | [androidJvm]<br>@Inject<br>constructor(reservationDao: [ReservationDao](../../com.example.skyreserve.database.room.dao/-reservation-dao/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteReservation](delete-reservation.md) | [androidJvm]<br>suspend fun [deleteReservation](delete-reservation.md)(reservation: [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)) |
| [getAllReservations](get-all-reservations.md) | [androidJvm]<br>fun [getAllReservations](get-all-reservations.md)(): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)&gt;&gt; |
| [insertReservation](insert-reservation.md) | [androidJvm]<br>suspend fun [insertReservation](insert-reservation.md)(reservation: [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)) |
| [updateReservation](update-reservation.md) | [androidJvm]<br>suspend fun [updateReservation](update-reservation.md)(reservation: [Reservation](../../com.example.skyreserve.database.room.entity/-reservation/index.md)) |
