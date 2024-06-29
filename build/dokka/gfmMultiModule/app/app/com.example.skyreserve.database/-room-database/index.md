//[app](../../../index.md)/[com.example.skyreserve.database](../index.md)/[RoomDatabase](index.md)

# RoomDatabase

[androidJvm]\
abstract class [RoomDatabase](index.md) : [RoomDatabase](https://developer.android.com/reference/kotlin/androidx/room/RoomDatabase.html)

## Constructors

| | |
|---|---|
| [RoomDatabase](-room-database.md) | [androidJvm]<br>constructor() |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [backingFieldMap](index.md#155738858%2FProperties%2F510797961) | [androidJvm]<br>val [backingFieldMap](index.md#155738858%2FProperties%2F510797961): [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; |
| [invalidationTracker](index.md#-990093491%2FProperties%2F510797961) | [androidJvm]<br>open val [invalidationTracker](index.md#-990093491%2FProperties%2F510797961): [InvalidationTracker](https://developer.android.com/reference/kotlin/androidx/room/InvalidationTracker.html) |
| [isOpen](index.md#-277138657%2FProperties%2F510797961) | [androidJvm]<br>open val [isOpen](index.md#-277138657%2FProperties%2F510797961): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [openHelper](index.md#-1864821605%2FProperties%2F510797961) | [androidJvm]<br>open val [openHelper](index.md#-1864821605%2FProperties%2F510797961): [SupportSQLiteOpenHelper](https://developer.android.com/reference/kotlin/androidx/sqlite/db/SupportSQLiteOpenHelper.html) |
| [queryExecutor](index.md#-177284564%2FProperties%2F510797961) | [androidJvm]<br>open val [queryExecutor](index.md#-177284564%2FProperties%2F510797961): [Executor](https://developer.android.com/reference/kotlin/java/util/concurrent/Executor.html) |
| [suspendingTransactionId](index.md#1027959380%2FProperties%2F510797961) | [androidJvm]<br>val [suspendingTransactionId](index.md#1027959380%2FProperties%2F510797961): [ThreadLocal](https://developer.android.com/reference/kotlin/java/lang/ThreadLocal.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [transactionExecutor](index.md#722320214%2FProperties%2F510797961) | [androidJvm]<br>open val [transactionExecutor](index.md#722320214%2FProperties%2F510797961): [Executor](https://developer.android.com/reference/kotlin/java/util/concurrent/Executor.html) |

## Functions

| Name | Summary |
|---|---|
| [assertNotMainThread](index.md#-917214377%2FFunctions%2F510797961) | [androidJvm]<br>open fun [assertNotMainThread](index.md#-917214377%2FFunctions%2F510797961)() |
| [assertNotSuspendingTransaction](index.md#1166251624%2FFunctions%2F510797961) | [androidJvm]<br>open fun [assertNotSuspendingTransaction](index.md#1166251624%2FFunctions%2F510797961)() |
| [beginTransaction](index.md#1020009182%2FFunctions%2F510797961) | [androidJvm]<br>open fun [~~beginTransaction~~](index.md#1020009182%2FFunctions%2F510797961)() |
| [clearAllTables](index.md#404244410%2FFunctions%2F510797961) | [androidJvm]<br>abstract fun [clearAllTables](index.md#404244410%2FFunctions%2F510797961)() |
| [close](index.md#1674273423%2FFunctions%2F510797961) | [androidJvm]<br>open fun [close](index.md#1674273423%2FFunctions%2F510797961)() |
| [compileStatement](index.md#162913197%2FFunctions%2F510797961) | [androidJvm]<br>open fun [compileStatement](index.md#162913197%2FFunctions%2F510797961)(sql: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SupportSQLiteStatement](https://developer.android.com/reference/kotlin/androidx/sqlite/db/SupportSQLiteStatement.html) |
| [endTransaction](index.md#622722960%2FFunctions%2F510797961) | [androidJvm]<br>open fun [~~endTransaction~~](index.md#622722960%2FFunctions%2F510797961)() |
| [flightDao](flight-dao.md) | [androidJvm]<br>abstract fun [flightDao](flight-dao.md)(): [FlightDao](../../com.example.skyreserve.database.room.dao/-flight-dao/index.md) |
| [getAutoMigrations](index.md#178130989%2FFunctions%2F510797961) | [androidJvm]<br>open fun [getAutoMigrations](index.md#178130989%2FFunctions%2F510797961)(autoMigrationSpecs: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;out [AutoMigrationSpec](https://developer.android.com/reference/kotlin/androidx/room/migration/AutoMigrationSpec.html)&gt;, [AutoMigrationSpec](https://developer.android.com/reference/kotlin/androidx/room/migration/AutoMigrationSpec.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Migration](https://developer.android.com/reference/kotlin/androidx/room/migration/Migration.html)&gt; |
| [getRequiredAutoMigrationSpecs](index.md#1623281881%2FFunctions%2F510797961) | [androidJvm]<br>open fun [getRequiredAutoMigrationSpecs](index.md#1623281881%2FFunctions%2F510797961)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;out [AutoMigrationSpec](https://developer.android.com/reference/kotlin/androidx/room/migration/AutoMigrationSpec.html)&gt;&gt; |
| [getTypeConverter](index.md#-194849133%2FFunctions%2F510797961) | [androidJvm]<br>open fun &lt;[T](index.md#-194849133%2FFunctions%2F510797961)&gt; [getTypeConverter](index.md#-194849133%2FFunctions%2F510797961)(klass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](index.md#-194849133%2FFunctions%2F510797961)&gt;): [T](index.md#-194849133%2FFunctions%2F510797961)? |
| [init](index.md#1039887154%2FFunctions%2F510797961) | [androidJvm]<br>open fun [init](index.md#1039887154%2FFunctions%2F510797961)(configuration: [DatabaseConfiguration](https://developer.android.com/reference/kotlin/androidx/room/DatabaseConfiguration.html)) |
| [inTransaction](index.md#-1889647314%2FFunctions%2F510797961) | [androidJvm]<br>open fun [inTransaction](index.md#-1889647314%2FFunctions%2F510797961)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [query](index.md#604106995%2FFunctions%2F510797961) | [androidJvm]<br>open fun [query](index.md#604106995%2FFunctions%2F510797961)(query: [SupportSQLiteQuery](https://developer.android.com/reference/kotlin/androidx/sqlite/db/SupportSQLiteQuery.html), signal: [CancellationSignal](https://developer.android.com/reference/kotlin/android/os/CancellationSignal.html)?): [Cursor](https://developer.android.com/reference/kotlin/android/database/Cursor.html)<br>open fun [query](index.md#-1376474873%2FFunctions%2F510797961)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), args: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt;?): [Cursor](https://developer.android.com/reference/kotlin/android/database/Cursor.html) |
| [reservationDao](reservation-dao.md) | [androidJvm]<br>abstract fun [reservationDao](reservation-dao.md)(): [ReservationDao](../../com.example.skyreserve.database.room.dao/-reservation-dao/index.md) |
| [runInTransaction](index.md#1063989044%2FFunctions%2F510797961) | [androidJvm]<br>open fun [runInTransaction](index.md#1063989044%2FFunctions%2F510797961)(body: [Runnable](https://developer.android.com/reference/kotlin/java/lang/Runnable.html))<br>open fun &lt;[V](index.md#-1842697888%2FFunctions%2F510797961)&gt; [runInTransaction](index.md#-1842697888%2FFunctions%2F510797961)(body: [Callable](https://developer.android.com/reference/kotlin/java/util/concurrent/Callable.html)&lt;[V](index.md#-1842697888%2FFunctions%2F510797961)&gt;): [V](index.md#-1842697888%2FFunctions%2F510797961) |
| [setTransactionSuccessful](index.md#954356125%2FFunctions%2F510797961) | [androidJvm]<br>open fun [~~setTransactionSuccessful~~](index.md#954356125%2FFunctions%2F510797961)() |
| [userAccountDao](user-account-dao.md) | [androidJvm]<br>abstract fun [userAccountDao](user-account-dao.md)(): [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md) |
