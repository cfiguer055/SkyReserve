package com.example.skyreserve.Util

import org.junit.Test
import LocalSessionManager
import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.*
import org.junit.Before
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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(context.getSharedPreferences("AppSessionPrefs", Context.MODE_PRIVATE)).thenReturn(prefs)
        `when`(prefs.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.putLong(anyString(), anyLong())).thenReturn(editor)
        `when`(editor.remove(anyString())).thenReturn(editor)

        sessionManager = LocalSessionManager(context)
    }

    @Test
    fun testCreateLoginSession() {
        val userEmail = "user@example.com"
        val token = "sample_token"

        sessionManager.createLoginSession(userEmail, token)

        verify(editor).putString("user_id", userEmail)
        verify(editor).putString("session_token", token)
        verify(editor).putLong(eq("token_expiry"), anyLong())
        verify(editor).apply()
    }
}