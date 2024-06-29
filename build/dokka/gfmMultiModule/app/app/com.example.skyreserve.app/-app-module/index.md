//[app](../../../index.md)/[com.example.skyreserve.app](../index.md)/[AppModule](index.md)

# AppModule

[androidJvm]\
@Module

class [AppModule](index.md)

## Constructors

| | |
|---|---|
| [AppModule](-app-module.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [provideAuthRepository](provide-auth-repository.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideAuthRepository](provide-auth-repository.md)(userAccountDao: [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md)): [AuthRepository](../../com.example.skyreserve.repository/-auth-repository/index.md) |
| [provideDatabase](provide-database.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideDatabase](provide-database.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [RoomDatabase](../../com.example.skyreserve.database/-room-database/index.md) |
| [provideLocalSessionManager](provide-local-session-manager.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideLocalSessionManager](provide-local-session-manager.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [LocalSessionManager](../../com.example.skyreserve.util/-local-session-manager/index.md) |
| [provideUserAccountDao](provide-user-account-dao.md) | [androidJvm]<br>@Provides<br>fun [provideUserAccountDao](provide-user-account-dao.md)(database: [RoomDatabase](../../com.example.skyreserve.database/-room-database/index.md)): [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md) |
| [provideUserAccountRepository](provide-user-account-repository.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideUserAccountRepository](provide-user-account-repository.md)(database: [RoomDatabase](../../com.example.skyreserve.database/-room-database/index.md)): [UserAccountRepository](../../com.example.skyreserve.repository/-user-account-repository/index.md) |
| [provideUserInteractionLogger](provide-user-interaction-logger.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideUserInteractionLogger](provide-user-interaction-logger.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [UserInteractionLogger](../../com.example.skyreserve.util/-user-interaction-logger/index.md) |
