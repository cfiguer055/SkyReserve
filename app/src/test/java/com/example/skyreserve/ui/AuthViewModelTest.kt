package com.example.skyreserve.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.ui.authentication.AuthViewModel
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.Util.SignUpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for the AuthViewModel class, focusing on the authentication methods.
 * These tests validate the behavior of signIn and signUp methods in different scenarios.
 * It also tests Network availability checks.
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var sessionManager: LocalSessionManager

    @Mock
    private lateinit var userAccountDao: UserAccountDao

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    private lateinit var authViewModel: AuthViewModel

    // Dispatcher for running coroutine tests
    private val testDispatcher = TestCoroutineDispatcher()


    /**
     * Set up the test environment before each test.
     * Initialize dependencies and inject them into the AuthViewModel.
     * Mocks the network and context-related services.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val mockNetwork = Mockito.mock(Network::class.java)


        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(mockNetwork)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(networkCapabilities)
        authViewModel = AuthViewModel(authRepository, sessionManager, context)

    }

    /**
     * Clean up the test environment after each test.
     */
    @After
    fun tearDown() {
        // Resets the main dispatcher to the original state.
        Dispatchers.resetMain()
        // Cleans up the TestCoroutineDispatcher.
        testDispatcher.cleanupTestCoroutines()
    }


    /**
     * Tests the signIn method of the AuthViewModel with valid credentials.
     * Verifies that the signInResult LiveData emits [SignInResult.SUCCESS].
     * Ensures that the user is logged in after a successful sign-in.
     */
    @Test
    fun `signIn with valid credentials returns SUCCESS`() = runTest {
        val email = "test@example.com"
        val password = "Password123"
        val userAccount = UserAccount(emailAddress = email, password = password)

        // -----------Test-Preparation---------------
        /*
        When testing a specific function or component in isolation, we often want to isolate the behavior of
        the unit under test from its dependencies.
        In this case, the signIn function of authViewModel is being tested. It interacts with several dependencies:
            - authRepository: The repository responsible for handling authentication-related tasks such as
                signing in, checking if an email exists, and retrieving user accounts by email address.
            - sessionManager: A manager responsible for managing user sessions, including checking if a token is valid.
        By mocking the behavior of these dependencies using Mockito's `when` method, we can control what values
        are returned when the signIn function calls these dependencies. This allows us to simulate different
        scenarios (e.g., successful sign-in, email already existing, valid token) and focus solely on testing
        the logic within the signIn function without relying on the actual implementations of its dependencies.
         */
        // Mocking dependencies for testing the signIn function:
            // 1. Mocking the signIn function to return true when called with provided email and password.
            //    This simulates a successful sign-in operation.
            // 2. Mocking the isEmailExisting function to return true when called with provided email.
            //    This ensures that the email address is considered existing.
            // 3. Mocking the network availability. SignIn() requires network availability check by expecting
            //    a Boolean value.
            // 4. Mocking the getUserAccountByEmailAddress function to return the provided user account.
            //    This provides a user account object for further processing.
            // 5. Mocking the isTokenValid function to return true after creating a login session.
            //    This ensures that the token is considered valid after creating the session.
        //
        // "val signInSuccess = authRepository.signIn(emailAddress, password)"
        `when`(authRepository.signIn(email, password)).thenReturn(true)
        // "val isEmailExisting = authRepository.isEmailExisting(emailAddress)"
        `when`(authRepository.isEmailExisting(email)).thenReturn(true)
        // Boolean value is expected for "if(isNetworkAvailable())"
        `when`(authViewModel.isNetworkAvailable()).thenReturn(true)
        // "val userAccount = authRepository.getUserAccountByEmailAddress(emailAddress)"
        `when`(authRepository.getUserAccountByEmailAddress(email)).thenReturn(userAccount)
        // after "sessionManager.createLoginSession(it.emailAddress, token)"
        //`when`(sessionManager.isTokenValid()).thenReturn(true)

        // Observing the signInResult LiveData
        val observer = Mockito.mock(Observer::class.java) as Observer<SignInResult>
        authViewModel.signInResult.observeForever(observer)

        // -------------Test----------------
        //
        // Calling the signIn function
        authViewModel.signIn(email, password)

        // logging the result for troubleshooting
//        authViewModel.signInResult.observeForever {
//            println("signInResult: $it")
//            observer.onChanged(it)
//        }


        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        // Verifying the result
        Mockito.verify(observer).onChanged(SignInResult.SUCCESS)
        assertEquals(authViewModel.isLoggedIn.value, true)

        // Direct assertion for signUpResult
        assertEquals(SignInResult.SUCCESS, authViewModel.signInResult.value)

        // Clean up observer
        authViewModel.signInResult.removeObserver(observer)
    }


    /**
     * Tests the signIn method of the AuthViewModel when invalid credentials are provided.
     * It verifies that the signInResult LiveData emits [SignInResult.INVALID_CREDENTIALS] and
     * ensures that the user is not logged in after the sign-in attempt.
     */
    @Test
    fun `signIn with invalid credentials returns INVALID_CREDENTIALS`() = runTest {
        val email = "invalid@example.com"
        val password = "wrongpassword"

        `when`(authRepository.signIn(email, password)).thenReturn(false)
        `when`(authRepository.isEmailExisting(email)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignInResult>
        authViewModel.signInResult.observeForever(observer)

        authViewModel.signIn(email, password)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        Mockito.verify(observer).onChanged(SignInResult.INVALID_CREDENTIALS)
        assertEquals(authViewModel.isLoggedIn.value, false)

        // Direct assertion for signUpResult
        assertEquals(SignInResult.INVALID_CREDENTIALS, authViewModel.signInResult.value)

        // Clean up observer
        authViewModel.signInResult.removeObserver(observer)
    }


    @Test
    fun `signIn with network error return NETWORK_ERROR` () = runTest {
        val email = "test@example.com"
        val password = "Password123"

        `when`(authRepository.signIn(email, password)).thenReturn(true)
        `when`(authRepository.isEmailExisting(email)).thenReturn(true)

        // Simulate network unavailability
        `when`(authViewModel.isNetworkAvailable()).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignInResult>
        authViewModel.signInResult.observeForever(observer)

        authViewModel.signIn(email, password)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        Mockito.verify(observer).onChanged(SignInResult.NETWORK_ERROR)
        assertEquals(authViewModel.isLoggedIn.value, false)

        // Direct assertion for signUpResult
        assertEquals(SignInResult.NETWORK_ERROR, authViewModel.signInResult.value)

        // Clean up observer
        authViewModel.signInResult.removeObserver(observer)
    }


    @Test
    fun `unexpected signIn failure return UNKNOWN_ERROR` () = runTest {
        val email = "test@example.com"
        val password = "Password123"

        `when`(authRepository.signIn(email, password)).thenReturn(false)
        `when`(authRepository.isEmailExisting(email)).thenReturn(false)
        `when`(authViewModel.isNetworkAvailable()).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignInResult>
        authViewModel.signInResult.observeForever(observer)

        authViewModel.signIn(email, password)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        Mockito.verify(observer).onChanged(SignInResult.UNKNOWN_ERROR)
        assertEquals(authViewModel.isLoggedIn.value, false)

        // Direct assertion for signUpResult
        assertEquals(SignInResult.UNKNOWN_ERROR, authViewModel.signInResult.value)

        // Clean up observer
        authViewModel.signInResult.removeObserver(observer)
    }


    /**
     * Tests the signUp method of the AuthViewModel with valid information.
     * Verifies that the signUpResult LiveData emits [SignUpResult.SUCCESS].
     * Ensures that the user is logged in after a successful sign-up.
     */
    @Test
    fun `signUp with valid information returns SUCCESS`() = runTest  {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"

        // val userAccount = UserAccount(emailAddress = email, password = password)

        // Mock isEmailExisting(email) in AuthRepository
        `when`(authRepository.isEmailExisting(email)).thenReturn(false)
        // Mock signUp() in AuthRepository
        `when`(authRepository.signUp(email, password)).thenReturn(true)
        // Mock getUserAccountByEmailAddress(email) in AuthRepository
        // Mocking the network availability check in AuthViewModel
        `when`(authViewModel.isNetworkAvailable()).thenReturn(true)

        // Observing the signUpResult LiveData
        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        // Calling the signUp function
        authViewModel.signUp(email, password, confirmPassword)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        // Verifying the result
        Mockito.verify(observer).onChanged(SignUpResult.SUCCESS)
        assertEquals(authViewModel.isLoggedIn.value, true)

        // Direct assertion for signUpResult
        assertEquals(SignUpResult.SUCCESS, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }


    /**
     * Tests the signUp method of the AuthViewModel with invalid email.
     * Verifies that the signUpResult LiveData emits [SignUpResult.INVALID_EMAIL].
     */
    @Test
    fun `signUp with invalid email returns INVALID_EMAIL`() = runTest {
        val email = "invalidemail"
        val password = "Password123"
        val confirmPassword = "Password123"


        //`when`(authRepository.isEmailExisting(email)).thenReturn(false)
        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        // Trigger the signUp action
        authViewModel.signUp(email, password, confirmPassword)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        // Verify that the LiveData was updated with the correct value
        Mockito.verify(observer).onChanged(SignUpResult.INVALID_EMAIL)
        assertEquals(SignUpResult.INVALID_EMAIL, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }


    /**
     * Tests the signUp method of the AuthViewModel with existing email.
     * Verifies that the signUpResult LiveData emits [SignUpResult.EXISTING_EMAIL].
     * Ensures that the user is not logged in after a unsuccessful sign-up.
     */
    @Test
    fun `signUp with existing email returns EXISTING_EMAIL`() = runTest  {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"


        // Mock authRepository
        //
        // Mock isEmailExisting(email) in AuthRepository
        `when`(authRepository.isEmailExisting(email)).thenReturn(true)
        // Mocking the network availability check in AuthViewModel
        `when`(authViewModel.isNetworkAvailable()).thenReturn(true)

        // Observing the signUpResult LiveData
        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)


        // Calling the signUp function
        authViewModel.signUp(email, password, confirmPassword)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        // Verifying the result
        Mockito.verify(observer).onChanged(SignUpResult.EXISTING_EMAIL)
        assertEquals(authViewModel.isLoggedIn.value, false)

        // Direct assertion for signUpResult
        assertEquals(SignUpResult.EXISTING_EMAIL, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }


    /**
     * Tests the signUp method of the AuthViewModel with short password.
     * Verifies that the signUpResult LiveData emits [SignUpResult.SHORT_PASSWORD].
     */
    @Test
    fun `signUp with short password returns SHORT_PASSWORD`() = runTest {
        val email = "newuser@example.com"
        val password = "Pass1"
        val confirmPassword = "Pass1"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        // Trigger the signUp action
        authViewModel.signUp(email, password, confirmPassword)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        // Verify that the LiveData was updated with the correct value
        Mockito.verify(observer).onChanged(SignUpResult.SHORT_PASSWORD)
        assertEquals(SignUpResult.SHORT_PASSWORD, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }


    /**
     * Tests the signUp method of the AuthViewModel with missing capital letter in password.
     * Verifies that the signUpResult LiveData emits [SignUpResult.MISSING_CAPITAL_LETTER].
     */
    @Test
    fun `signUp with missing capital letter returns MISSING_CAPITAL_LETTER`() = runTest {
        val email = "newuser@example.com"
        val password = "password123"
        val confirmPassword = "password123"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        Mockito.verify(observer).onChanged(SignUpResult.MISSING_CAPITAL_LETTER)
        assertEquals(SignUpResult.MISSING_CAPITAL_LETTER, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }

    /**
     * Tests the signUp method of the AuthViewModel with missing lowercase letter in password.
     * Verifies that the signUpResult LiveData emits [SignUpResult.MISSING_LOWCASE_LETTER].
     */
    @Test
    fun `signUp with missing lowercase letter returns MISSING_LOWCASE_LETTER`() = runTest {
        val email = "newuser@example.com"
        val password = "PASSWORD123"
        val confirmPassword = "PASSWORD123"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        // Advance coroutines to completion of pending jobs, important for LiveData updates
        advanceUntilIdle()

        Mockito.verify(observer).onChanged(SignUpResult.MISSING_LOWCASE_LETTER)
        assertEquals(SignUpResult.MISSING_LOWCASE_LETTER, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }

    /**
     * Tests the signUp method of the AuthViewModel with missing digit in password.
     * Verifies that the signUpResult LiveData emits [SignUpResult.MISSING_DIGIT].
     */
    @Test
    fun `signUp with missing digit returns MISSING_DIGIT`() = runTest {
        val email = "newuser@example.com"
        val password = "Password"
        val confirmPassword = "Password"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        Mockito.verify(observer).onChanged(SignUpResult.MISSING_DIGIT)
        assertEquals(SignUpResult.MISSING_DIGIT, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }

    /**
     * Tests the signUp method of the AuthViewModel with password mismatch.
     * Verifies that the signUpResult LiveData emits [SignUpResult.PASSWORD_NO_MATCH].
     */
    @Test
    fun `signUp with password mismatch returns PASSWORD_NO_MATCH`() = runTest {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password321"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        Mockito.verify(observer).onChanged(SignUpResult.PASSWORD_NO_MATCH)
        assertEquals(SignUpResult.PASSWORD_NO_MATCH, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }

    /**
     * Tests the signUp method of the AuthViewModel with missing information.
     * Verifies that the signUpResult LiveData emits [SignUpResult.MISSING_INFORMATION].
     */
    @Test
    fun `signUp with missing information returns MISSING_INFORMATION`() = runTest {
        val email = ""
        val password = "Password123"
        val confirmPassword = "Password123"

        //`when`(authRepository.isEmailExisting(email)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        Mockito.verify(observer).onChanged(SignUpResult.MISSING_INFORMATION)
        assertEquals(SignUpResult.MISSING_INFORMATION, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }


    /*
    @Test
    fun `signUp without terms of service being accepted`() = runTest {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"
    }
     */


    /**
     * Tests the signUp method of the AuthViewModel without network availability.
     * Verifies that the signUpResult LiveData emits [SignUpResult.NETWORK_ERROR].
     */
    @Test
    fun `signUp without network returns NETWORK_ERROR`() = runTest {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)
        `when`(authViewModel.isNetworkAvailable()).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        Mockito.verify(observer).onChanged(SignUpResult.NETWORK_ERROR)
        assertEquals(SignUpResult.NETWORK_ERROR, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }

    /**
     * Tests the signUp method of the AuthViewModel with unknown error.
     * Verifies that the signUpResult LiveData emits [SignUpResult.UNKNOWN_ERROR].
     */
    @Test
    fun `signUp with unknown error returns UNKNOWN_ERROR`() = runTest {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)
        `when`(authRepository.signUp(email, password)).thenReturn(false)
        `when`(authViewModel.isNetworkAvailable()).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        // logging the result for troubleshooting
//        authViewModel.signUpResult.observeForever {
//            println("signUpResult: $it")
//            observer.onChanged(it)
//        }

        Mockito.verify(observer).onChanged(SignUpResult.UNKNOWN_ERROR)
        assertEquals(SignUpResult.UNKNOWN_ERROR, authViewModel.signUpResult.value)

        // Clean up observer
        authViewModel.signUpResult.removeObserver(observer)
    }


    /**
     * Tests the isNetworkAvailable method of the AuthViewModel.
     * Verifies that it returns false when the network is not available.
     */
    @Test
    fun `isNetworkAvailable returns false when network is not available`() {
        `when`(connectivityManager.activeNetwork).thenReturn(null)

        val result = authViewModel.isNetworkAvailable()

        assertFalse(result)
    }


    /**
     * Tests the isNetworkAvailable method of the AuthViewModel.
     * Verifies that it returns true when the network is available.
     */
    @Test
    fun `isNetworkAvailable returns true when network is available`() {
        val mockNetwork = Mockito.mock(Network::class.java)
        `when`(connectivityManager.activeNetwork).thenReturn(mockNetwork)
        `when`(connectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)

        val result = authViewModel.isNetworkAvailable()

        assertTrue(result)
    }


    /**
     * Tests the validateSession method of the AuthViewModel.
     * Verifies that it returns true for a valid token and false for an invalid token.
     */
    @Test
    fun `validateSession returns true for valid token`() {
        `when`(sessionManager.isTokenValid()).thenReturn(true)
        assertTrue(authViewModel.validateSession())
    }

    @Test
    fun `validateSession returns false for invalid token`() {
        `when`(sessionManager.isTokenValid()).thenReturn(false)
        assertFalse(authViewModel.validateSession())
    }

    /**
     * Tests the refreshSessionToken method of the AuthViewModel.
     * Verifies that the session token is renewed correctly.
     */
    @Test
    fun `refreshSessionToken renews the token`() {
        // Call the method to be tested
        authViewModel.refreshSessionToken()

        // Verify that the sessionManager.renewToken() was called
        Mockito.verify(sessionManager).renewToken()
    }

    /**
     * Tests the logout method of the AuthViewModel.
     * Verifies that it clears the user session and updates the relevant states.
     */
    @Test
    fun `logout clears the user session`() {
        // Call the method to be tested
        authViewModel.logout()

        // Verify that the sessionManager.logoutUser() was called
        Mockito.verify(sessionManager).logoutUser()

        // Verify that the ViewModel states are updated
        assertFalse(authViewModel.isLoggedIn.value)
        assertNull(authViewModel.currentUser.value)
    }
}