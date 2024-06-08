package com.example.skyreserve.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        Assert.assertEquals(success, true)
    }


}