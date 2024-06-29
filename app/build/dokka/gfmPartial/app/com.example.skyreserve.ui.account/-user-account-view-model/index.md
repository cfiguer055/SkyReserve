//[app](../../../index.md)/[com.example.skyreserve.ui.account](../index.md)/[UserAccountViewModel](index.md)

# UserAccountViewModel

[androidJvm]\
class [UserAccountViewModel](index.md)@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Inject","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Inject<!--- --->constructor(userAccountRepository: [UserAccountRepository](../../com.example.skyreserve.repository/-user-account-repository/index.md), sessionManager: [LocalSessionManager](../../com.example.skyreserve.util/-local-session-manager/index.md), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel for managing user account details. Handles fetching, inserting, and updating user details.

## Constructors

| | |
|---|---|
| [UserAccountViewModel](-user-account-view-model.md) | [androidJvm]<br>@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Inject","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Inject<!--- ---><br>constructor(userAccountRepository: [UserAccountRepository](../../com.example.skyreserve.repository/-user-account-repository/index.md), sessionManager: [LocalSessionManager](../../com.example.skyreserve.util/-local-session-manager/index.md), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Properties

| Name | Summary |
|---|---|
| [updateStatus](update-status.md) | [androidJvm]<br>val [updateStatus](update-status.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [userDetails](user-details.md) | [androidJvm]<br>val [userDetails](user-details.md): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[UserData](../../com.example.skyreserve.util/-user-data/index.md)?&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961) | [androidJvm]<br>open fun [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [clear](clear.md) | [androidJvm]<br>fun [clear](clear.md)()<br>Clears the user details and update status. |
| [fetchUserDetails](fetch-user-details.md) | [androidJvm]<br>fun [fetchUserDetails](fetch-user-details.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Fetches user details by email and updates LiveData. Observes the data from the repository and maps it to UserData. |
| [getUserEmail](get-user-email.md) | [androidJvm]<br>fun [getUserEmail](get-user-email.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Retrieves the user email if the session is valid. |
| [insertUserDetails](insert-user-details.md) | [androidJvm]<br>fun [insertUserDetails](insert-user-details.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userData: [UserData](../../com.example.skyreserve.util/-user-data/index.md))<br>Inserts new user details into the database. Updates the LiveData based on the success of the operation. |
| [updateUserDetails](update-user-details.md) | [androidJvm]<br>fun [updateUserDetails](update-user-details.md)(userData: [UserData](../../com.example.skyreserve.util/-user-data/index.md))<br>Updates existing user details in the database. Updates the LiveData based on the success of the operation. |
| [validateSession](validate-session.md) | [androidJvm]<br>fun [validateSession](validate-session.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Validates the current session. |
