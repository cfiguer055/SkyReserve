package com.example.skyreserve.UI

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skyreserve.Database.Dao.UserAccountDao
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.SignInResult
import com.example.skyreserve.Util.SignUpResult
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

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

    // 1 test fail
    private val testDispatcher = TestCoroutineDispatcher()

    // 4 test fails / 3 with runTest
    //private val testDispatcher = StandardTestDispatcher()

    // 3 test fails /
    //private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        //hiltRule.inject()

        Dispatchers.setMain(testDispatcher)
        val mockNetwork = Mockito.mock(Network::class.java)


        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(mockNetwork)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(networkCapabilities)
        authViewModel = AuthViewModel(authRepository, sessionManager, context)

    }

//    @Before
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
    fun `signIn with valid credentials returns SUCCESS`() = runTest {
        val email = "test@example.com"
        val password = "password"
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

        // Verifying the result
        Mockito.verify(observer).onChanged(SignInResult.SUCCESS)
        assertEquals(authViewModel.isLoggedIn.value, true)
    }


    @Test
    fun `signUp with valid information returns SUCCESS`() = runTest  {
        val email = "newuser@example.com"
        val password = "Password123"
        val confirmPassword = "Password123"

        val userAccount = UserAccount(emailAddress = email, password = password)

        //`when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(null)
        //doNothing().`when`(userAccountDao).insertUserAccount(any(UserAccount::class.java))

        // Mocking the DAO methods
//        runBlocking {
//            `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(null)
//            doNothing().`when`(userAccountDao).insertUserAccount(any(UserAccount::class.java))
//        }
//
//         //Mocking the repository methods
//        `when`(authRepository.signUp(email, password)).thenAnswer {
//            runBlocking {
//                userAccountDao.insertUserAccount(userAccount)
//                true
//            }
//        }

        // Mock authRepository
        //
        // Mock isEmailExisting(email) in AuthRepository
        `when`(authRepository.isEmailExisting(email)).thenReturn(false)
        // Mock signUp() in AuthRepository
        `when`(authRepository.signUp(email, password)).thenReturn(true)
        // Mock getUserAccountByEmailAddress(email) in AuthRepository
        // Mocking the network availability check in AuthViewModel
        `when`(authViewModel.isNetworkAvailable()).thenReturn(true)
        //`when`(authRepository.getUserAccountByEmailAddress(email)).thenReturn(userAccount)





        // Observing the signUpResult LiveData
//        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
//        authViewModel.signUpResult.observeForever(observer)



        // Observing the signUpResult LiveData and logging the result
        val observer = Mockito.mock(Observer::class.java) as Observer<SignUpResult>
        authViewModel.signUpResult.observeForever {
            println("signUpResult: $it")
            observer.onChanged(it)
        }


        // -------------Test----------------
        //
        // Calling the signUp function
        authViewModel.signUp(email, password, confirmPassword)

        // Ensure LiveData changes are observed
        advanceUntilIdle()

        // Log current state of signUpResult
        println("Current signUpResult: ${authViewModel.signUpResult.value}")


        // Verifying the result
        Mockito.verify(observer).onChanged(SignUpResult.SUCCESS)
        assertEquals(authViewModel.isLoggedIn.value, true)


        // Direct assertion for signUpResult
        assertEquals(SignUpResult.SUCCESS, authViewModel.signUpResult.value)
    }

    @Test
    fun `signIn with invalid credentials returns INVALID_CREDENTIALS`() = runTest {
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