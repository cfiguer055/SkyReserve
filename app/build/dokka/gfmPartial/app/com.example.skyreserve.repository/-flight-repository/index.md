//[app](../../../index.md)/[com.example.skyreserve.repository](../index.md)/[FlightRepository](index.md)

# FlightRepository

[androidJvm]\
@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Singleton","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Singleton<!--- --->

class [FlightRepository](index.md)@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Inject","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Inject<!--- --->constructor(flightDao: [FlightDao](../../com.example.skyreserve.database.room.dao/-flight-dao/index.md))

## Constructors

| | |
|---|---|
| [FlightRepository](-flight-repository.md) | [androidJvm]<br>@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Inject","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Inject<!--- ---><br>constructor(flightDao: [FlightDao](../../com.example.skyreserve.database.room.dao/-flight-dao/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteFlight](delete-flight.md) | [androidJvm]<br>suspend fun [deleteFlight](delete-flight.md)(flight: [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)) |
| [getAllFlights](get-all-flights.md) | [androidJvm]<br>fun [getAllFlights](get-all-flights.md)(): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)&gt;&gt; |
| [insertFlight](insert-flight.md) | [androidJvm]<br>suspend fun [insertFlight](insert-flight.md)(flight: [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)) |
| [updateFlight](update-flight.md) | [androidJvm]<br>suspend fun [updateFlight](update-flight.md)(flight: [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)) |
