package com.example.skyreserve.repository

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UserAccountRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userAccountDao: UserAccountDao

    private lateinit var userAccountRepository: UserAccountRepository

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Initialize the Android framework mocking in your test class
        MockKAnnotations.init(this)
        // Mock the main looper used by LiveData
        mockkStatic(Looper::class)
        every { Looper.getMainLooper() } returns mockk(relaxed = true)


        userAccountRepository = UserAccountRepository(userAccountDao)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `insertUserAccount returns true if user account is inserted to database`() = runTest {
        val email = "valid-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        // Mock the DAO to simulate successful database insertion
        `when`(userAccountDao.insertUserAccount(userAccount)).thenAnswer {
            // Simulate a successful insertion
            Unit
        }
        val success = userAccountRepository.insertUserAccount(userAccount)

        advanceUntilIdle()

        Assert.assertEquals(true, success)
    }


    @Test
    fun `insertUserAccount returns false if database insertion of user account fails`() = runTest {
        val email = "invalid-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        // Mock the DAO to throw an exception, simulating a database insertion failure
        `when`(userAccountDao.insertUserAccount(userAccount)).thenThrow(RuntimeException("DB Insertion failed"))

        val success = userAccountRepository.insertUserAccount(userAccount)

        advanceUntilIdle()

        Assert.assertEquals(false, success)
    }


    @Test
    fun `updateUserAccount returns true if user account is updated in the database`() = runTest {
        val email = "update-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        // Mock the DAO to simulate successful database update
        `when`(userAccountDao.updateUserAccount(userAccount)).thenReturn(Unit)

        val success = userAccountRepository.updateUserAccount(userAccount)

        Assert.assertTrue(success)
    }

    @Test
    fun `updateUserAccount returns false if database update of user account fails`() = runTest {
        val email = "fail-update@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        // Mock the DAO to throw an exception, simulating a database update failure
        `when`(userAccountDao.updateUserAccount(userAccount)).thenThrow(RuntimeException("DB Update failed"))

        val success = userAccountRepository.updateUserAccount(userAccount)

        Assert.assertFalse(success)
    }

    @Test
    fun `deleteUserAccount returns true if user account is deleted from database`() = runTest {
        val email = "delete-email@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        // Mock the DAO to simulate successful database deletion
        `when`(userAccountDao.deleteUserAccount(userAccount)).thenReturn(Unit)
        val success = userAccountRepository.deleteUserAccount(userAccount)

        Assert.assertTrue(success)
    }

    @Test
    fun `deleteUserAccount returns false if database deletion of user account fails`() = runTest {
        val email = "fail-delete@gmail.com"
        val password = "Password123"

        val userAccount = UserAccount(email, password)

        // Mock the DAO to throw an exception, simulating a database deletion failure
        `when`(userAccountDao.deleteUserAccount(userAccount)).thenThrow(RuntimeException("DB Deletion failed"))
        val success = userAccountRepository.deleteUserAccount(userAccount)

        Assert.assertFalse(success)
    }


    @Test
    fun `getUserAccountByEmailAddress returns user account if email is found in database`() = runTest {
        val email = "found-email@gmail.com"
        val userAccount = UserAccount(email, "Password123")

        // Mock the DAO to return a user account
        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))
        val result = userAccountRepository.getUserAccountByEmailAddress(email)

        Assert.assertEquals(userAccount, result)
    }

    @Test
    fun `getUserAccountByEmailAddress returns null if email is not found in database`() = runTest {
        val email = "not-found-email@gmail.com"

        // Mock the DAO to return null
        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))
        val result = userAccountRepository.getUserAccountByEmailAddress(email)

        Assert.assertNull(result)
    }



    // ---------------- UserAccountRepo Wrappers -----------------------

//    @Test
//    fun `getUserAccountByEmailAddressAsLiveData returns LiveData with correct user account`() = runTest {
//        val email = "live-data-email@gmail.com"
//        val userAccount = UserAccount(email, "Password123")
//
//        // Mock the DAO to return a specific user account
//        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))
//
//        // Get LiveData and observe it
//        val liveData = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
//        val observedData = mutableListOf<UserAccount?>()
//        val observer = Observer<UserAccount?> { observedData.add(it) }
//
//        liveData.observeForever(observer)
//        advanceUntilIdle()
//
//        Assert.assertEquals(listOf(userAccount), observedData)
//        liveData.removeObserver(observer)
//    }
//    @Test
//    fun `getUserAccountByEmailAddressAsLiveData returns LiveData with correct user account`() = runTest {
//        val email = "live-data-email@gmail.com"
//        val userAccount = UserAccount(email, "Password123")
//
//        // Mock the DAO to return a specific user account
//        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))
//
//        // Get LiveData and observe it
//        val liveData = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
//        val observedData = mutableListOf<UserAccount?>()
//        liveData.observeForever { observedData.add(it) }
//
//        advanceUntilIdle()
//
//        Assert.assertEquals(listOf(userAccount), observedData)
//        liveData.removeObserver { observedData.remove(it) }
//    }
    @Test
    fun `getUserAccountByEmailAddressAsLiveData returns LiveData with correct user account`() = runTest {
        val email = "live-data-email@gmail.com"
        val userAccount = UserAccount(email, "Password123")

        // Mock the DAO to return a specific user account
        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))

        // Get LiveData and observe it
        val liveData = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
        val observedData = mutableListOf<UserAccount?>()

        // Observer setup
        val observer = Observer<UserAccount?> {
            observedData.add(it)
        }
        liveData.observeForever(observer)

        advanceUntilIdle() // Make sure all pending operations are completed

        Assert.assertEquals(listOf(userAccount), observedData)

        liveData.removeObserver(observer)
    }


}
