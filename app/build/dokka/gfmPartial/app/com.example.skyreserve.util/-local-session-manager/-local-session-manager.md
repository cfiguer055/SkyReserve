//[app](../../../index.md)/[com.example.skyreserve.util](../index.md)/[LocalSessionManager](index.md)/[LocalSessionManager](-local-session-manager.md)

# LocalSessionManager

[androidJvm]\
constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

Primary constructor to initialize LocalSessionManager in testing (To not mock Encryption SharedPreferences) Secondary constructor to initialize LocalSessionManager using Context. This maintains backward compatibility with existing code that passes Context.

Initializes the SharedPreferences using EncryptedSharedPreferences.

#### Parameters

androidJvm

| | |
|---|---|
| context | the context used to create EncryptedSharedPreferences |

[androidJvm]\
constructor(prefs: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html))
