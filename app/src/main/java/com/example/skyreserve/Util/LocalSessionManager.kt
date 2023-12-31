import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*

class LocalSessionManager(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    //private val prefs: SharedPreferences = context.getSharedPreferences("AppSessionPrefs", Context.MODE_PRIVATE)
    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        "AppSessionPrefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun createLoginSession(userEmail: String, token: String) {
        val editor = prefs.edit()
        editor.putString("user_id", userEmail)
        editor.putString("session_token", token)
        // Set a new expiry time, e.g., 24 hours from now
        editor.putLong("token_expiry", System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        editor.apply()
    }

    fun logoutUser() {
        val editor = prefs.edit()
        editor.remove("user_email")
        editor.remove("session_token")
        editor.remove("token_expiry")
        editor.apply()
    }

    // Checks if the current token is valid
    fun isTokenValid(): Boolean {
        val token = getUserToken()
        val tokenExpiry = getTokenExpiry()
        return token != null && tokenExpiry > System.currentTimeMillis()
    }

    // Renews the token
    fun renewToken() {
        val userEmail = getUserEmail() ?: return
        val newToken = UUID.randomUUID().toString()
        createLoginSession(userEmail, newToken)
    }

    private fun getUserToken(): String? {
        // Retrieves the session token from SharedPreferences
        return prefs.getString("session_token", null)
    }

    private fun getTokenExpiry(): Long {
        return prefs.getLong("token_expiry", -1)
    }

    public fun getUserEmail(): String? {
        return prefs.getString("user_id", null)
    }
}
