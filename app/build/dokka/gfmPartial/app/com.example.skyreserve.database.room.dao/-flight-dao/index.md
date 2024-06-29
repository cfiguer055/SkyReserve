//[app](../../../index.md)/[com.example.skyreserve.database.room.dao](../index.md)/[FlightDao](index.md)

# FlightDao

[androidJvm]\
interface [FlightDao](index.md)

## Functions

| Name | Summary |
|---|---|
| [deleteFlight](delete-flight.md) | [androidJvm]<br>abstract suspend fun [deleteFlight](delete-flight.md)(flight: [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)) |
| [getAllFlightsFlow](get-all-flights-flow.md) | [androidJvm]<br>abstract fun [getAllFlightsFlow](get-all-flights-flow.md)(): <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"kotlinx.coroutines.flow","classNames":"Flow","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Flow<!--- --->&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)&gt;&gt; |
| [getFlightById](get-flight-by-id.md) | [androidJvm]<br>abstract suspend fun [getFlightById](get-flight-by-id.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)? |
| [insertFlight](insert-flight.md) | [androidJvm]<br>abstract suspend fun [insertFlight](insert-flight.md)(flight: [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)) |
| [updateFlight](update-flight.md) | [androidJvm]<br>abstract suspend fun [updateFlight](update-flight.md)(flight: [Flight](../../com.example.skyreserve.database.room.entity/-flight/index.md)) |
