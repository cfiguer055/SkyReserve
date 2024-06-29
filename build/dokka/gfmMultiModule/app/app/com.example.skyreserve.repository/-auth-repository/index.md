//[app](../../../index.md)/[com.example.skyreserve.repository](../index.md)/[AuthRepository](index.md)

# AuthRepository

[androidJvm]\
@Singleton

class [AuthRepository](index.md)@Injectconstructor(userAccountDao: [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md))

## Constructors

| | |
|---|---|
| [AuthRepository](-auth-repository.md) | [androidJvm]<br>@Inject<br>constructor(userAccountDao: [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getUserAccountByEmailAddress](get-user-account-by-email-address.md) | [androidJvm]<br>fun [getUserAccountByEmailAddress](get-user-account-by-email-address.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)?&gt; |
| [isEmailExisting](is-email-existing.md) | [androidJvm]<br>fun [isEmailExisting](is-email-existing.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [signIn](sign-in.md) | [androidJvm]<br>fun [signIn](sign-in.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [signUp](sign-up.md) | [androidJvm]<br>fun [signUp](sign-up.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
