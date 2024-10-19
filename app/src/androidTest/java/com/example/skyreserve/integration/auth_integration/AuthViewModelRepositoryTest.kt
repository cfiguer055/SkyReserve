package com.example.skyreserve.integration.auth_integration

import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthViewModelRepositoryTest {

    @Test
    fun test_sign_in_with_valid_credentials_returns_true() = runTest {}

    @Test
    fun test_sign_in_with_invalid_credentials_returns_false() = runTest {}

    @Test
    fun test_sign_in_with_non_existent_email_returns_false() = runTest {}

    @Test
    fun test_sign_up_with_new_email_returns_true() = runTest {}

    @Test
    fun test_sign_up_with_existing_email_returns_false() = runTest {}

    @Test
    fun test_is_email_existing_with_existing_email_returns_true() = runTest {}

    @Test
    fun test_is_email_existing_with_non_existent_email_returns_false() = runTest {}

    @Test
    fun test_get_user_account_by_email_address_with_existing_email_returns_user() = runTest {}

    @Test
    fun test_get_user_account_by_email_address_with_non_existent_email_returns_null() = runTest {}

}