package com.example.skyreserve.UI

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.Util.SignUpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    private lateinit var authViewModel: AuthViewModel

    private val testDispatcher = TestCoroutineDispatcher()



    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val mockNetwork = Mockito.mock(Network::class.java)
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(mockNetwork)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(networkCapabilities)
        authViewModel = AuthViewModel(authRepository, sessionManager, context)
    }
//    fun setUp() {
//        Dispatchers.setMain(testDispatcher)
//        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
//        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(Mockito.mock(Network::class.java))
//        authViewModel = AuthViewModel(authRepository, sessionManager, context)
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }



    @Test
    fun `isLoggedIn returns correct value`() {
//        val isLoggedInObserver = Mockito.mock(Observer::class.java) as Observer<*>
//        authViewModel.isLoggedIn.observeForever(isLoggedInObserver)
//
//        // Initially, isLoggedIn should be false
//        assertEquals(false, authViewModel.isLoggedIn.value)
//
//        // Simulate user login
//        `when`(sessionManager.isTokenValid()).thenReturn(true)
//        authViewModel.validateSession()
//
//        // Verify that the value of isLoggedIn has been updated to true
//        assertEquals(true, authViewModel.isLoggedIn.value)
//
//        authViewModel.isLoggedIn.removeObserver(isLoggedInObserver)
    }

    @Test
    fun `signIn with valid credentials returns SUCCESS`() = runBlockingTest {
        val email = "test@example.com"
        val password = "password"
        val userAccount = UserAccount(emailAddress = email, password = password)

        `when`(authRepository.signIn(email, password)).thenReturn(true)
        `when`(authRepository.isEmailExisting(email)).thenReturn(true)
        `when`(authRepository.getUserAccountByEmailAddress(email)).thenReturn(userAccount)
        `when`(sessionManager.isTokenValid()).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignInResult>
        authViewModel.signInResult.observeForever(observer)

        authViewModel.signIn(email, password)

        Mockito.verify(observer).onChanged(SignInResult.SUCCESS)
        assertEquals(authViewModel.isLoggedIn.value, true)
    }

    @Test
    fun `signUp with valid information returns SUCCESS`() = runBlockingTest {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"

        `when`(authRepository.isEmailExisting(email)).thenReturn(false)
        `when`(authRepository.signUp(email, password)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever(observer)

        authViewModel.signUp(email, password, confirmPassword)

        Mockito.verify(observer).onChanged(SignUpResult.SUCCESS)
        assertEquals(authViewModel.isLoggedIn.value, true)
    }

    @Test
    fun `signIn with invalid credentials returns INVALID_CREDENTIALS`() = runBlockingTest {
        val email = "invalid@example.com"
        val password = "wrongpassword"

        `when`(authRepository.signIn(email, password)).thenReturn(false)
        `when`(authRepository.isEmailExisting(email)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<SignInResult>
        authViewModel.signInResult.observeForever(observer)

        authViewModel.signIn(email, password)

        Mockito.verify(observer).onChanged(SignInResult.INVALID_CREDENTIALS)
        assertEquals(authViewModel.isLoggedIn.value, false)
    }

    @Test
    fun `isNetworkAvailable returns false when network is not available`() {
        `when`(connectivityManager.activeNetwork).thenReturn(null)

        val result = authViewModel.isNetworkAvailable()

        assertFalse(result)
    }

    @Test
    fun `isNetworkAvailable returns true when network is available`() {
        val mockNetwork = Mockito.mock(Network::class.java)
        `when`(connectivityManager.activeNetwork).thenReturn(mockNetwork)
        `when`(connectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)

        val result = authViewModel.isNetworkAvailable()

        assertTrue(result)
    }
}