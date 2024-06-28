package com.example.skyreserve.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skyreserve.database.room.dao.UserAccountDao
import com.example.skyreserve.database.room.entity.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mindrot.jbcrypt.BCrypt
import org.mockito.Mockito.*


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

        userAccountDao = mock(UserAccountDao::class.java)
        authRepository = AuthRepository(userAccountDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `signIn returns true if email and password match`() = runTest {
        val email = "valid-email@gmail.com"
        val password = "ValidPassword123"
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        val userAccount = UserAccount(email, hashedPassword)

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))

        val result = authRepository.signIn(email, password).first()
        Assert.assertTrue(result)
    }

    @Test
    fun `signIn returns false if email is not found`() = runTest {
        val email = "not-found-email@gmail.com"
        val password = "Password123"

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))

        val result = authRepository.signIn(email, password).first()
        Assert.assertFalse(result)
    }

    @Test
    fun `signIn returns false if password does not match`() = runTest {
        val email = "valid-email@gmail.com"
        val password = "WrongPassword123"
        val userAccount = UserAccount(email, BCrypt.hashpw("CorrectPassword123", BCrypt.gensalt()))

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))

        val result = authRepository.signIn(email, password).first()
        Assert.assertFalse(result)
    }

    @Test
    fun `signUp returns true if new user is successfully created`() = runTest {
        val email = "new-email@gmail.com"
        val password = "NewPassword123"
        //val userAccount = UserAccount(email, BCrypt.hashpw(password, BCrypt.gensalt()))

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))
        //`when`(userAccountDao.insertUserAccount(userAccount)).thenReturn(Unit)

        val result = authRepository.signUp(email, password).first()
        Assert.assertTrue(result)
    }
// k
    @Test
    fun `signUp returns false if email already exists`() = runTest {
        val email = "existing-email@gmail.com"
        val password = "Password123"
        val existingUserAccount = UserAccount(email, BCrypt.hashpw(password, BCrypt.gensalt()))

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(existingUserAccount))

        val result = authRepository.signUp(email, password).first()
        Assert.assertFalse(result)
    }

    @Test
    fun `signUp returns false if insertion fails`() = runTest  {
        val email = "new-email@gmail.com"
        val password = "NewPassword123"
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        //val userAccount = UserAccount(email, hashedPassword)

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenThrow(RuntimeException("DB Query failed"))

        val result = authRepository.signUp(email, password).first()

        advanceUntilIdle()

        Assert.assertFalse(result)
    }

    @Test
    fun `isEmailExisting returns true if email is found`() = runTest {
        val email = "found-email@gmail.com"
        val userAccount = UserAccount(email, "Password123")

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(userAccount))

        val result = authRepository.isEmailExisting(email).first()
        Assert.assertTrue(result)
    }

    @Test
    fun `isEmailExisting returns false if email is not found`() = runTest {
        val email = "not-found-email@gmail.com"

        `when`(userAccountDao.getUserAccountByEmailAddress(email)).thenReturn(flowOf(null))

        val result = authRepository.isEmailExisting(email).first()
        Assert.assertFalse(result)
    }
}
