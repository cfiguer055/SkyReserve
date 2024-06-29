//[app](../../../index.md)/[com.example.skyreserve.repository](../index.md)/[UserAccountRepository](index.md)/[getUserAccountByEmailAddress](get-user-account-by-email-address.md)

# getUserAccountByEmailAddress

[androidJvm]\
fun [getUserAccountByEmailAddress](get-user-account-by-email-address.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)?&gt;

Fetches a user account by email address from the database. The result is emitted as a Flow of UserAccount, which may be null if the account does not exist.
