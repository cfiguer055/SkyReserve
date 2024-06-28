package com.example.skyreserve.integration.auth_integration

import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthFullStackTest {

    @Test
    fun test_valid_sign_in_flow_updates_UI_correctly() = runTest {}

    @Test
    fun test_invalid_sign_in_shows_error() = runTest {}

    @Test
    fun test_valid_sign_up_flow_updates_UI_correctly() = runTest {}

    @Test
    fun test_sign_up_with_existing_email_shows_email_taken_error() = runTest {}

    @Test
    fun test_logout_updates_UI_to_logged_out_state() = runTest {}

    @Test
    fun test_network_error_during_sign_in_shows_network_error() = runTest {}

    @Test
    fun test_password_mismatch_during_sign_up_shows_error() = runTest {}

    @Test
    fun test_invalid_email_during_sign_up_shows_error() = runTest {}


}