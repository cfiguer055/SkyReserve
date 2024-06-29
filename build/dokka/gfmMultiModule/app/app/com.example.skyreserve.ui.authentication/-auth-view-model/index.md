//[app](../../../index.md)/[com.example.skyreserve.ui.authentication](../index.md)/[AuthViewModel](index.md)

# AuthViewModel

[androidJvm]\
class [AuthViewModel](index.md)@Injectconstructor(authRepository: [AuthRepository](../../com.example.skyreserve.repository/-auth-repository/index.md), sessionManager: [LocalSessionManager](../../com.example.skyreserve.util/-local-session-manager/index.md), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [AuthViewModel](-auth-view-model.md) | [androidJvm]<br>@Inject<br>constructor(authRepository: [AuthRepository](../../com.example.skyreserve.repository/-auth-repository/index.md), sessionManager: [LocalSessionManager](../../com.example.skyreserve.util/-local-session-manager/index.md), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Properties

| Name | Summary |
|---|---|
| [currentUser](current-user.md) | [androidJvm]<br>val [currentUser](current-user.md): StateFlow&lt;[UserAccount](../../com.example.skyreserve.database.room.entity/-user-account/index.md)?&gt; |
| [isLoggedIn](is-logged-in.md) | [androidJvm]<br>val [isLoggedIn](is-logged-in.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [signInResult](sign-in-result.md) | [androidJvm]<br>val [signInResult](sign-in-result.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[SignInResult](../../com.example.skyreserve.util/-sign-in-result/index.md)&gt; |
| [signUpResult](sign-up-result.md) | [androidJvm]<br>val [signUpResult](sign-up-result.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[SignUpResult](../../com.example.skyreserve.util/-sign-up-result/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961) | [androidJvm]<br>open fun [addCloseable](../../com.example.skyreserve.ui.seat_map/-seat-map-view-model/index.md#264516373%2FFunctions%2F510797961)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [isNetworkAvailable](is-network-available.md) | [androidJvm]<br>fun [isNetworkAvailable](is-network-available.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [logout](logout.md) | [androidJvm]<br>fun [logout](logout.md)() |
| [refreshSessionToken](refresh-session-token.md) | [androidJvm]<br>fun [refreshSessionToken](refresh-session-token.md)() |
| [signIn](sign-in.md) | [androidJvm]<br>fun [signIn](sign-in.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [signUp](sign-up.md) | [androidJvm]<br>fun [signUp](sign-up.md)(emailAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), confirmPassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [validateSession](validate-session.md) | [androidJvm]<br>fun [validateSession](validate-session.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
