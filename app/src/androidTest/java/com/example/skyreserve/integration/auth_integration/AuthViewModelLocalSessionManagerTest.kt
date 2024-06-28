package com.example.skyreserve.integration.auth_integration

import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthViewModelLocalSessionManagerTest {

    @Test
    fun signIn_with_valid_credentials_creates_login_session_in_LocalSessionManager() = runTest {}

    @Test
    fun signUp_with_valid_new_user_creates_login_session_in_LocalSessionManager() = runTest {}

    @Test
    fun validateSession_returns_true_when_session_is_valid() = runTest {}

    @Test
    fun validateSession_returns_false_when_session_is_invalid() = runTest {}

    @Test
    fun refreshSessionToken_updates_the_token_in_LocalSessionManager() = runTest {}

    @Test
    fun logout_clears_session_in_LocalSessionManager() = runTest {}

    @Test
    fun logout_sets_isLoggedIn_to_false() = runTest {}

    @Test
    fun logout_clears_currentUser() = runTest {}


}