//[app](../../../index.md)/[com.example.skyreserve.util](../index.md)/[LocalSessionManager](index.md)

# LocalSessionManager

[androidJvm]\
@<!---  GfmCommand {"@class":"org.jetbrains.dokka.gfm.ResolveLinkGfmCommand","dri":{"packageName":"javax.inject","classNames":"Singleton","callable":null,"target":{"@class":"org.jetbrains.dokka.links.PointingToDeclaration"},"extra":null}} --->Singleton<!--- --->

class [LocalSessionManager](index.md)(prefs: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html))

## Constructors

| | |
|---|---|
| [LocalSessionManager](-local-session-manager.md) | [androidJvm]<br>constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))<br>Primary constructor to initialize LocalSessionManager in testing (To not mock Encryption SharedPreferences) Secondary constructor to initialize LocalSessionManager using Context. This maintains backward compatibility with existing code that passes Context.<br>constructor(prefs: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) |

## Functions

| Name | Summary |
|---|---|
| [createLoginSession](create-login-session.md) | [androidJvm]<br>fun [createLoginSession](create-login-session.md)(userEmail: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getUserEmail](get-user-email.md) | [androidJvm]<br>fun [getUserEmail](get-user-email.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [isTokenValid](is-token-valid.md) | [androidJvm]<br>fun [isTokenValid](is-token-valid.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [logoutUser](logout-user.md) | [androidJvm]<br>fun [logoutUser](logout-user.md)() |
| [renewToken](renew-token.md) | [androidJvm]<br>fun [renewToken](renew-token.md)() |
