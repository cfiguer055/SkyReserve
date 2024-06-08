package com.example.skyreserve.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skyreserve.database.room.dao.UserAccountDao
import junit.framework.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Singleton


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

}