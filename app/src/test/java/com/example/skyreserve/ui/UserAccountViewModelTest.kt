package com.example.skyreserve.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skyreserve.Util.LocalSessionManager
import com.example.skyreserve.Util.UserData
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import com.example.skyreserve.repository.UserAccountRepository
import com.example.skyreserve.ui.account.UserAccountViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
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


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

       userAccountViewModel = UserAccountViewModel(userAccountRepository, sessionManager, context)

    }


    @After
    fun tearDown() {
        // Resets the main dispatcher to the original state.
        Dispatchers.resetMain()
        // Cleans up the TestCoroutineDispatcher.
        testDispatcher.cleanupTestCoroutines()
    }


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

        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(userAccount)

        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
        userAccountViewModel.userDetails.observeForever(observer)

        userAccountViewModel.fetchUserDetails(email)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(expectedUserData)

        userAccountViewModel.userDetails.removeObserver(observer)
    }


    @Test
    fun `fetchUserDetails does not retrieve userDetails LiveData with invalid email`() = runTest {
        val email = "invalid_email@gmail.com"
        val expectedUserData = null

        `when`(userAccountRepository.getUserAccountByEmailAddress(email)).thenReturn(null)

        val observer = Mockito.mock(Observer::class.java) as Observer<UserData?>
        userAccountViewModel.userDetails.observeForever(observer)

        userAccountViewModel.fetchUserDetails(email)

        advanceUntilIdle() // This replaces advanceTimeBy and ensures all coroutines are executed

        Mockito.verify(observer).onChanged(expectedUserData)

        userAccountViewModel.userDetails.removeObserver(observer)
    }


    @Test
    fun `insertUserDetails adds and updates with non-existing email`() = kotlin.run {

    }


    @Test
    fun `insertUserDetails returns false with existing email`() = kotlin.run {

    }
}



















