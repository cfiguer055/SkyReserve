package com.example.skyreserve.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.skyreserve.util.LocalSessionManager
import com.example.skyreserve.util.UserData
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.UserAccountRepository
import com.example.skyreserve.ui.account.UserAccountViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


/**
 * Unit tests for the UserAccountViewModel class to validate the functionality of user account management,
 * including fetching, inserting, and updating user details. These tests ensure that the ViewModel
 * handles data correctly across various scenarios using mocked dependencies.
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UserAccountViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userAccountRepository: UserAccountRepository

    @Mock
    private lateinit var sessionManager: LocalSessionManager

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var userAccountDao: UserAccountDao

    private lateinit var userAccountViewModel: UserAccountViewModel

    // Dispatcher for running coroutine tests
    private val testDispatcher = TestCoroutineDispatcher()


    /**
     * Setup method initializes the ViewModel with mocked dependencies and sets the main dispatcher
     * for coroutines to the test dispatcher to control coroutine timing.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        userAccountViewModel = UserAccountViewModel(userAccountRepository, sessionManager, context)
    }


    /**
     * Tear down method resets the main dispatcher and cleans up the test coroutine environment
     * after each test execution to avoid interference.
     */
    @After
    fun tearDown() {
        // Resets the main dispatcher to the original state.
        Dispatchers.resetMain()
        // Cleans up the TestCoroutineDispatcher.
        testDispatcher.cleanupTestCoroutines()
    }


    /**
     * Verifies that valid email details are correctly fetched and LiveData is updated accordingly.
     */
    @Test
    fun `fetchUserDetails retrieves userDetails LiveData with valid email`() = runTest {
        val email = "test@gmail.com"
        val userAccount = UserAccount(
            emailAddress = email, password = "Password123",
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123"
        )
        val expectedUserData = UserData(firstName="John", lastName="Doe", gender="Male", phone="1234567890", dateOfBirth="1990-01-01", address="123 Street", stateCode="NY", countryCode="US", passport="ABC123")

        // Mock LiveData
        val liveData = MutableLiveData<UserAccount>()
        liveData.value = userAccount
        `when`(userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)).thenReturn(liveData)

        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
        userAccountViewModel.userDetails.observeForever(observer)

        userAccountViewModel.fetchUserDetails(email)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(expectedUserData)

        userAccountViewModel.userDetails.removeObserver(observer)
    }


    /**
     * Tests fetching user details with an invalid email to ensure LiveData does not update with user data.
     */
    @Test
    fun `fetchUserDetails does not retrieve userDetails LiveData with invalid email`() = runTest {
        val email = "invalid_email@gmail.com"
        val expectedUserData = null

        val liveData = MutableLiveData<UserAccount?>()
        liveData.value = null

        `when`(userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)).thenReturn(liveData)

        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
        userAccountViewModel.userDetails.observeForever(observer)

        userAccountViewModel.fetchUserDetails(email)

        advanceUntilIdle()

        Mockito.verify(observer).onChanged(expectedUserData)

        userAccountViewModel.userDetails.removeObserver(observer)
    }
//    @Test
//    fun `fetchUserDetails does not retrieve userDetails LiveData with invalid email`() = runTest {
//        val email = "invalid_email@gmail.com"
//        val expectedUserData = null
//
//        val userAccount = UserAccount(
//            emailAddress = email, password = "Password123",
//            firstName = "John", lastName = "Doe",
//            gender = "Male", phone = "1234567890",
//            dateOfBirth = "1990-01-01",
//            address = "123 Street", stateCode = "NY",
//            countryCode = "US", passport = "ABC123"
//        )
//
//        // Mock LiveData
//        val liveData = MutableLiveData<UserAccount>()
//        liveData.value = userAccount
//        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(null)
//
//        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
//        userAccountViewModel.userDetails.observeForever(observer)
//
//        userAccountViewModel.fetchUserDetails(email)
//
//        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed
//
//        Mockito.verify(observer).onChanged(expectedUserData)
//
//        userAccountViewModel.userDetails.removeObserver(observer)
//    }


    /**
     * Tests insertion of new user details with a non-existing email, verifying correct LiveData updates.
     */
    @Test
    fun `insertUserDetails adds and updates with non-existing email`() = runTest {
        val email = "nonexisting-email@gmail.com"
        val password = "Password123"

        val userDetails = UserData(
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123")

        val userAccount = UserAccount(email, password, userDetails)

        `when`(sessionManager.getUserEmail()).thenReturn(null)
        `when`(userAccountRepository.insertUserAccount(userAccount)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
        userAccountViewModel.userDetails.observeForever(observer)

        userAccountViewModel.insertUserDetails(email, password, userDetails)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(userDetails)

        Assert.assertEquals(userAccountViewModel.userDetails.value, userDetails)
        Assert.assertEquals(userAccountViewModel.updateStatus.value, true)

        userAccountViewModel.userDetails.removeObserver(observer)
    }


    /**
     * Confirms that insertion of user details with an existing email results in no data update and correct LiveData values.
     */
    @Test
    fun `insertUserDetails returns false with existing email`() = runTest{
        val email = "existing-email@gmail.com"
        val password = "Password123"

        val userDetails = UserData(
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123")

        //val userAccount = UserAccount(email, password, userDetails)

        `when`(sessionManager.getUserEmail()).thenReturn(email)

        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
        userAccountViewModel.userDetails.observeForever(observer)

        userAccountViewModel.insertUserDetails(email, password, userDetails)

        advanceUntilIdle()

        Mockito.verify(observer).onChanged(null)

        Assert.assertEquals(userAccountViewModel.userDetails.value, null)
        Assert.assertEquals(userAccountViewModel.updateStatus.value, false)

        userAccountViewModel.userDetails.removeObserver(observer)
    }


    /**
     * Tests updating user details when the user account already exists in the database.
     * Verifies that the operation is successful and LiveData values are updated accordingly.
     */
    @Test
    fun `updateUserDetails returns true if user account exists`() = runTest {
        val email = "existing-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        val userDetails = UserData(
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123")


        `when`(sessionManager.getUserEmail()).thenReturn(email)
        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(userAccount)
        `when`(userAccountRepository.updateUserAccount(userAccount)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        userAccountViewModel.updateStatus.observeForever(observer)

        userAccountViewModel.updateUserDetails(userDetails)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(true)

        Assert.assertEquals(userAccountViewModel.updateStatus.value, true)
        Assert.assertEquals(userAccountViewModel.userDetails.value, userDetails)

        userAccountViewModel.updateStatus.removeObserver(observer)
    }


    /**
     * Tests the behavior of updating user details when no corresponding email exists in the local session.
     * Ensures that the operation fails and LiveData values are updated to reflect the failure.
     */
    @Test
    fun `updateUserDetails returns false if local session cannot retrieve email`() = runTest {
        val email = "nonexisting-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        val userDetails = UserData(
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123")


        `when`(sessionManager.getUserEmail()).thenReturn(null)
//        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))
//        `when`(userAccountRepository.updateUserAccount(userAccount)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        userAccountViewModel.updateStatus.observeForever(observer)

        userAccountViewModel.updateUserDetails(userDetails)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(false)

        Assert.assertEquals(userAccountViewModel.updateStatus.value, false)
        Assert.assertEquals(userAccountViewModel.userDetails.value, null)

        userAccountViewModel.updateStatus.removeObserver(observer)
    }


    /**
     * Tests the behavior of updating user details when no corresponding user account exists in the database.
     * Ensures that the operation fails and LiveData values are updated to reflect the failure.
     */
    @Test
    fun `updateUserDetails returns false if user account cannot be retrieved from database`() = runTest {
        val email = "nonexisting-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        val userDetails = UserData(
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123")


        `when`(sessionManager.getUserEmail()).thenReturn(email)
        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(null)
//        `when`(userAccountRepository.updateUserAccount(userAccount)).thenReturn(true)

        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        userAccountViewModel.updateStatus.observeForever(observer)

        userAccountViewModel.updateUserDetails(userDetails)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(false)

        Assert.assertEquals(userAccountViewModel.updateStatus.value, false)
        Assert.assertEquals(userAccountViewModel.userDetails.value, null)

        userAccountViewModel.updateStatus.removeObserver(observer)
    }


    /**
     * Tests updating user details when the database update operation fails.
     * Verifies that the operation fails and LiveData values are updated to indicate the failure.
     */
    @Test
    fun `updateUserDetails returns false if repository fails database update`() = runTest {
        val email = "existing-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        val userDetails = UserData(
            firstName = "John", lastName = "Doe",
            gender = "Male", phone = "1234567890",
            dateOfBirth = "1990-01-01",
            address = "123 Street", stateCode = "NY",
            countryCode = "US", passport = "ABC123")


        `when`(sessionManager.getUserEmail()).thenReturn(email)
        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(userAccount)
        `when`(userAccountRepository.updateUserAccount(userAccount)).thenReturn(false)

        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        userAccountViewModel.updateStatus.observeForever(observer)

        userAccountViewModel.updateUserDetails(userDetails)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(false)

        Assert.assertEquals(userAccountViewModel.updateStatus.value, false)
        Assert.assertEquals(userAccountViewModel.userDetails.value, null)

        userAccountViewModel.updateStatus.removeObserver(observer)
    }


    /**
     * Verifies that `validateSession` returns true when the session token is valid.
     */
    @Test
    fun `validateSession returns true if session is valid`() = runTest {
        `when`(sessionManager.isTokenValid()).thenReturn(true)
        val sessionValid = userAccountViewModel.validateSession()
        Assert.assertTrue(sessionValid)
    }


    /**
     * Verifies that `validateSession` returns false when the session token is invalid.
     */
    @Test
    fun `validateSession returns false if session is invalid`() = runTest {
        `when`(sessionManager.isTokenValid()).thenReturn(false)
        val sessionValid = userAccountViewModel.validateSession()
        Assert.assertFalse(sessionValid)
    }


    /**
     * Verifies that `getUserEmail` returns the correct email when the session is valid.
     */
    @Test
    fun `getUserEmail returns email when session is valid`() = runTest {
        `when`(sessionManager.isTokenValid()).thenReturn(true)
        `when`(sessionManager.getUserEmail()).thenReturn("test@example.com")
        val email = userAccountViewModel.getUserEmail()
        Assert.assertEquals("test@example.com", email)
    }


    /**
     * Tests `getUserEmail` to confirm it returns null when the session is invalid.
     */
    @Test
    fun `getUserEmail returns null when session is invalid`() = runTest {
        `when`(sessionManager.isTokenValid()).thenReturn(false)
        val email = userAccountViewModel.getUserEmail()
        Assert.assertNull(email)
    }


    /**
     * Verifies that calling `clear` resets user details and update status to their initial values.
     */
    @Test
    fun `clear sets userDetails and updateStatus to initial values`() = runTest {
        userAccountViewModel.clear()

        Assert.assertEquals(userAccountViewModel.userDetails.value, null)
        Assert.assertEquals(userAccountViewModel.updateStatus.value, false)
    }
}



















