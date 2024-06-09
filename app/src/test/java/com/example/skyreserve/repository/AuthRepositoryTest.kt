package com.example.skyreserve.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skyreserve.database.room.dao.UserAccountDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Test;



@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userAccountDao: UserAccountDao

    private lateinit var authRepository: AuthRepository

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        authRepository = AuthRepository(userAccountDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `signIn returns true i`() = runTest {

    }
}