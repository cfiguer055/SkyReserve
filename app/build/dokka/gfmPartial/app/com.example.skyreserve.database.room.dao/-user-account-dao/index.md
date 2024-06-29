//[app](../../../index.md)/[com.example.skyreserve.database.room.dao](../index.md)/[UserAccountDao](index.md)

# UserAccountDao

[androidJvm]\
interface [UserAccountDao](index.md)

## Functions

| Name | Summary |
|---|---|
| [deleteUserAccount](delete-user-account.md) | [androidJvm]<br>abstract suspend fun [deleteUserAccount](delete-user-account.md)(userAccount: [UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)) |
| [getAllUsersFlow](get-all-users-flow.md) | [androidJvm]<br>abstract fun [getAllUsersFlow](get-all-users-flow.md)(): <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"kotlinx.coroutines.flow","classNames":"Flow","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Flow<!--- --->&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)&gt;&gt; |
| [getUserAccountByEmailAddress](get-user-account-by-email-address.md) | [androidJvm]<br>abstract fun [getUserAccountByEmailAddress](get-user-account-by-email-address.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): <!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"kotlinx.coroutines.flow","classNames":"Flow","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Flow<!--- --->&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)?&gt; |
| [getUserReservations](get-user-reservations.md) | [androidJvm]<br>abstract fun [getUserReservations](get-user-reservations.md)(userId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[UserReservations](../../com.example.skyreserve.WhatToDo.DataTransferObject/-user-reservations/index.md)&gt; |
| [insertUserAccount](insert-user-account.md) | [androidJvm]<br>abstract suspend fun [insertUserAccount](insert-user-account.md)(userAccount: [UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)) |
| [updateUserAccount](update-user-account.md) | [androidJvm]<br>abstract suspend fun [updateUserAccount](update-user-account.md)(userAccount: [UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)) |
