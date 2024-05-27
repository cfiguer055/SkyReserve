package com.example.skyreserve.Util

import org.junit.Test
import LocalSessionManager
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class LocalSessionManagerTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var prefs: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var sessionManager: LocalSessionManager

    /**
     * Set up the test environment before each test.
     * This method initializes the mocks and the LocalSessionManager instance.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Mocking the behavior of SharedPreferences and its Editor
        `when`(context.getSharedPreferences("AppSessionPrefs", Context.MODE_PRIVATE)).thenReturn(prefs)
        `when`(prefs.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.putLong(anyString(), anyLong())).thenReturn(editor)
        `when`(editor.remove(anyString())).thenReturn(editor)

        // Initialize LocalSessionManager with the mocked SharedPreferences
        sessionManager = LocalSessionManager(prefs)
    }

    /**
     * Test for the createLoginSession function.
     * This test verifies that the function correctly stores the user email and token.
     */
    @Test
    fun testCreateLoginSession() {
        val userEmail = "user@example.com"
        val token = "sample_token"

        // Call the function to create a login session
        sessionManager.createLoginSession(userEmail, token)

        // Verify that the correct values were stored in SharedPreferences
        verify(editor).putString("user_id", userEmail)
        verify(editor).putString("session_token", token)
        verify(editor).putLong(eq("token_expiry"), anyLong())
        verify(editor).apply()
    }
}


// dependencies are commented out in build.gradle for below

// These dependencies were used to mock MasterKeys and EncryptedSharedPreferences in
// the old LocalSessionManager implementation:
// import org.powermock.api.mockito.PowerMockito
// import org.powermock.core.classloader.annotations.PrepareForTest
// import org.powermock.modules.junit4.PowerMockRunner

// Old LocalSessionManager implementation that used MasterKeys and EncryptedSharedPreferences for encryption
// class LocalSessionManager(context: Context) {
//     private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//
//     //private val prefs: SharedPreferences = context.getSharedPreferences("AppSessionPrefs", Context.MODE_PRIVATE)
//     private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
//         "AppSessionPrefs",
//         masterKeyAlias,
//         context,
//         EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//         EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//     )
// }