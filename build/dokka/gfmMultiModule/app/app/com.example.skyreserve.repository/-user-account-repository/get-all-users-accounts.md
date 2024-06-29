//[app](../../../index.md)/[com.example.skyreserve.repository](../index.md)/[UserAccountRepository](index.md)/[getAllUsersAccounts](get-all-users-accounts.md)

# getAllUsersAccounts

[androidJvm]\
fun [getAllUsersAccounts](get-all-users-accounts.md)(): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)&gt;&gt;

Retrieves a flow of all user accounts from the database. The data is fetched on the IO dispatcher for better performance and thread safety.
