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
import kotlinx.coroutines.flow.*
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


/**
 * Unit tests for the UserAccountRepository class to verify the behavior of its public functions.
 * These tests ensure that the repository correctly interacts with the database layer and handles
 * different data scenarios (insertions, updates, deletions, and queries) using mocked dependencies.
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UserAccountRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userAccountDao: UserAccountDao

    private lateinit var userAccountRepository: UserAccountRepository

    private val testDispatcher = TestCoroutineDispatcher()


    /**
     * Setup method initializes the repository with mocked DAO and sets the main dispatcher
     * for coroutines to the test dispatcher to control coroutine timing.
     */
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


    /**
     * Tear down method resets the main dispatcher and cleans up the test coroutine environment
     * after each test execution to avoid interference.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()
    }


    /**
     * Test to verify that inserting a valid user account into the database returns true,
     * indicating successful insertion.
     */
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

    /**
     * Test to verify that if the database insertion fails, the function returns false,
     * indicating the failure.
     */
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


    /**
     * Test to verify that updating an existing user account in the database returns true,
     * reflecting a successful update.
     */
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


    /**
     * Test to verify that if a database update operation fails, the function returns false.
     */
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


    /**
     * Verifies that a user account can be successfully deleted from the database.
     * This test mocks the DAO to simulate a successful deletion operation and checks if the repository method returns true.
     */
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


    /**
     * Tests the scenario where a user account deletion fails due to a database error.
     * The DAO is mocked to throw an exception, and the test checks if the repository method returns false.
     */
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


    /**
     * Tests if the repository can retrieve a user account from the database when the email is found.
     * It mocks the DAO to return a specific user account and checks if the repository correctly fetches this account.
     */
    @Test
    fun `getUserAccountByEmailAddress returns user account if email is found in database`() = runTest {
            val email = "found-email@gmail.com"
            val userAccount = UserAccount(email, "Password123")

            // Mock the DAO to return a user account
            `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))
            val result = userAccountRepository.getUserAccountByEmailAddress(email).firstOrNull()

            Assert.assertEquals(userAccount, result)
        }


    /**
     * Verifies that the repository returns null when attempting to fetch a user account with an email that does not exist in the database.
     * This test is important for handling cases where the user does not exist.
     */
    @Test
    fun `getUserAccountByEmailAddress returns null if email is not found in database`() = runTest {
        val email = "not-found-email@gmail.com"

        // Mock the DAO to return null
        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))
        val result = userAccountRepository.getUserAccountByEmailAddress(email).firstOrNull()

        Assert.assertNull(result)
    }


    // ---------------- UserAccountRepo Wrappers -----------------------


//    /**
//     * Ensures that the LiveData wrapper for getUserAccountByEmailAddress correctly emits a UserAccount when it exists.
//     * This test is crucial for UI components that depend on observing this LiveData.
//     */
//    @Test
//    fun `getUserAccountByEmailAddressAsLiveData returns LiveData with correct user account`() =
//        runTest {
//            val email = "live-data-email@gmail.com"
//            val userAccount = UserAccount(email, "Password123")
//
//            // Mock the DAO to return a specific user account
//            `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))
//
//            // Get LiveData and observe it
//            val liveData = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
//            val observedData = mutableListOf<UserAccount?>()
//
//            // Observer setup
//            val observer = Observer<UserAccount?> {
//                observedData.add(it)
//            }
//            liveData.observeForever(observer)
//
//            advanceUntilIdle() // Make sure all pending operations are completed
//
//            Assert.assertEquals(listOf(userAccount), observedData)
//
//
//            liveData.removeObserver(observer)
//        }
//
//
//    /**
//     * Tests the repository's LiveData wrapper for the scenario where no user account exists for the given email.
//     * This test ensures that the LiveData properly emits null, which is vital for UI error handling.
//     */
//    @Test
//    fun `getUserAccountByEmailAddressAsLiveData returns null LiveData for email not in database`() =
//        runTest {
//            val email = "null-live-email@gmail.com"
//            val userAccount = UserAccount(email, "Password123")
//
//            // Mock the DAO to return a specific user account
//            `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))
//
//            // Get LiveData and observe it
//            val liveData = userAccountRepository.getUserAccountByEmailAddressAsLiveData(email)
//            val observedData = mutableListOf<UserAccount?>()
//
//            // Observer setup
//            val observer = Observer<UserAccount?> {
//                observedData.add(it)
//            }
//            liveData.observeForever(observer)
//
//            advanceUntilIdle() // Make sure all pending operations are completed
//
//            Assert.assertTrue(observedData.all { it == null }) // Check that all observed items are null
//
//            liveData.removeObserver(observer)
//        }


    /**
     * Verifies that when there are no users in the database, the repository correctly returns an empty list.
     * This test is important for ensuring that the application can handle scenarios where no data is present without errors.
     */
    @Test
    fun `getAllUsersAccounts returns empty list when no users are present`() = runTest {
        // Mock the DAO to return an empty list
        `when`(userAccountDao.getAllUsersFlow()).thenReturn(flowOf(emptyList()))

        // Collect the result from the repository
        val result = userAccountRepository.getAllUsersAccounts().toList()

        // Assert that the result is an empty list
        Assert.assertTrue(result[0].isEmpty())
    }


    /**
     * Tests that the repository returns a list containing exactly one user when there is only one user in the database.
     * This test ensures that the repository correctly handles minimal data sets.
     */
    @Test
    fun `getAllUsersAccounts returns a list with one user when one user is present`() = runTest {
        val singleUser = UserAccount("email@domain.com", "Password123")
        // Mock the DAO to return a list with one user
        `when`(userAccountDao.getAllUsersFlow()).thenReturn(flowOf(listOf(singleUser)))

        // Collect the result from the repository
        val result = userAccountRepository.getAllUsersAccounts().toList()

        // Assert that the list contains exactly one user
        Assert.assertEquals(1, result[0].size)
        Assert.assertEquals(singleUser, result[0][0])
    }


    /**
     * Ensures that the repository can handle and return multiple user accounts correctly.
     * This test is critical for verifying the system's ability to process and present multiple data entries from the database.
     */
    @Test
    fun `getAllUsersAccounts returns a list with multiple users when multiple users are present`() =
        runTest {
            val userOne = UserAccount("user1@domain.com", "Password1")
            val userTwo = UserAccount("user2@domain.com", "Password2")
            val userThree = UserAccount("user3@domain.com", "Password3")
            // Mock the DAO to return a list with multiple users
            `when`(userAccountDao.getAllUsersFlow()).thenReturn(flowOf(listOf(userOne, userTwo, userThree)))

            // Collect the result from the repository
            val result = userAccountRepository.getAllUsersAccounts().toList()

            // Assert that the list contains the correct number of users
            Assert.assertEquals(3, result[0].size)
            Assert.assertTrue(result[0].containsAll(listOf(userOne, userTwo, userThree)))

        }
}