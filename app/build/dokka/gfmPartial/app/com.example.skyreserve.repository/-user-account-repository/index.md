//[app](../../../index.md)/[com.example.skyreserve.repository](../index.md)/[UserAccountRepository](index.md)

# UserAccountRepository

[androidJvm]\
@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Singleton","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Singleton<!--- --->

class [UserAccountRepository](index.md)@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Inject","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Inject<!--- --->constructor(userAccountDao: [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md))

## Constructors

| | |
|---|---|
| [UserAccountRepository](-user-account-repository.md) | [androidJvm]<br>@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Inject","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Inject<!--- ---><br>constructor(userAccountDao: [UserAccountDao](../../com.example.skyreserve.database.room.dao/-user-account-dao/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteUserAccount](delete-user-account.md) | [androidJvm]<br>suspend fun [deleteUserAccount](delete-user-account.md)(userAccount: [UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Deletes a user account from the database. This function returns a Boolean to indicate whether the deletion was successful. |
| [getAllUsersAccounts](get-all-users-accounts.md) | [androidJvm]<br>fun [getAllUsersAccounts](get-all-users-accounts.md)(): <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"kotlinx.coroutines.flow","classNames":"Flow","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Flow<!--- --->&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)&gt;&gt;<br>Retrieves a flow of all user accounts from the database. The data is fetched on the IO dispatcher for better performance and thread safety. |
| [getUserAccountByEmailAddress](get-user-account-by-email-address.md) | [androidJvm]<br>fun [getUserAccountByEmailAddress](get-user-account-by-email-address.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"kotlinx.coroutines.flow","classNames":"Flow","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Flow<!--- --->&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)?&gt;<br>Fetches a user account by email address from the database. The result is emitted as a Flow of UserAccount, which may be null if the account does not exist. |
| [insertUserAccount](insert-user-account.md) | [androidJvm]<br>suspend fun [insertUserAccount](insert-user-account.md)(userAccount: [UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Inserts a user account into the database. The operation is performed on the IO dispatcher and a Boolean result is returned indicating success. |
| [updateUserAccount](update-user-account.md) | [androidJvm]<br>suspend fun [updateUserAccount](update-user-account.md)(userAccount: [UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Updates a user account in the database. This function also runs on the IO dispatcher and returns a Boolean result. |
