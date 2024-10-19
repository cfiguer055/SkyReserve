package com.example.skyreserve.util

import org.junit.Test
import android.content.Context
import android.content.SharedPreferences

import org.junit.Assert.*
import org.junit.Before

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Unit tests for the LocalSessionManager class.
 * These tests validate the behavior of session management methods such as createLoginSession, logoutUser,
 * isTokenValid, and renewToken. Mocking is used to simulate the SharedPreferences and its Editor.
 */
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
        // Initialize Mockito annotations
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

    /**
     * Test for the logoutUser function.
     * This test verifies that the function correctly removes user-related data from SharedPreferences.
     */
    @Test
    fun testLogoutUser() {
        // Log out the user
        sessionManager.logoutUser()

        // Verify that the correct values were removed from SharedPreferences
        verify(editor).remove("user_email")
        verify(editor).remove("session_token")
        verify(editor).remove("token_expiry")
        verify(editor).apply()
    }

    /**
     * Test for the isTokenValid function.
     * This test verifies that the function correctly determines the token validity based on the expiry time.
     */
    @Test
    fun testIsTokenValid() {
        // Simulate a token expiry time 1 hour from now (valid token)
        val validTokenExpiry = System.currentTimeMillis() + 1000 * 60 * 60 // 1 hour from now
        `when`(prefs.getString("session_token", null)).thenReturn("sample_token")
        `when`(prefs.getLong("token_expiry", -1)).thenReturn(validTokenExpiry)
        // Verify that the token is valid
        assertTrue(sessionManager.isTokenValid())


        // Simulate a token expiry time 1 hour ago (expired token)
        val expiredTokenExpiry = System.currentTimeMillis() - 1000 * 60 * 60 // 1 hour ago
        `when`(prefs.getLong("token_expiry", -1)).thenReturn(expiredTokenExpiry)
        // Verify that the token is invalid
        assertFalse(sessionManager.isTokenValid())
    }

    /**
     * Test for the renewToken function.
     * This test verifies that the function correctly renews the session token and updates the expiry time.
     */
    @Test
    fun testRenewToken() {
        val userEmail = "user@example.com"

        // Mock the behavior of SharedPreferences to return the user email
        `when`(prefs.getString("user_id", null)).thenReturn(userEmail)

        // Renew the token
        sessionManager.renewToken()

        // Verify that the new token and updated expiry time were stored in SharedPreferences
        verify(editor).putString(eq("user_id"), eq(userEmail))
        verify(editor).putString(eq("session_token"), anyString())
        verify(editor).putLong(eq("token_expiry"), anyLong())
        verify(editor).apply()
    }
}
