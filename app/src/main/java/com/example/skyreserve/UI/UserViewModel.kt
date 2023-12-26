import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyreserve.Database.Entity.UserAccount
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.Util.SignUpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class UserViewModel(
    private val authRepository: AuthRepository, // Handles authentication logic
    private val sessionManager: LocalSessionManager // Manages local session state
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // LiveData or StateFlow to hold the sign-up result
    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> get() = _signUpResult

    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser: StateFlow<UserAccount?> = _currentUser

    // Function to attempt user login
    fun login(emailAddress: String, password: String) {
        viewModelScope.launch {
            if (authRepository.signIn(emailAddress, password)) {
                val userAccount = authRepository.getUserAccountByEmailAddress(emailAddress)
                _isLoggedIn.value = true
                _currentUser.value = userAccount
                userAccount?.let {
                    // Generate the token (ideally, this would be done by a secure server)
                    val token = UUID.randomUUID().toString()
                    // Create the login session with the generated token
                    sessionManager.createLoginSession(it.emailAddress, token)
                }
            } else {
                // Handle login failure, update UI accordingly
                _isLoggedIn.value = false
                _currentUser.value = null
            }
        }
    }

    // Function to attempt user sign-up
    fun signUp(emailAddress: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            val success = authRepository.signUp(emailAddress, password)
            _isLoggedIn.value = success
        }
    }

    // Function to log out the user
    fun logout() {
        sessionManager.logoutUser()
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    // Call this when the app starts or resumes to check if the user should remain logged in
    fun validateSession() {
        if (!sessionManager.isTokenValid()) {
            logout()
        }
    }

    // Call this method to renew the token
    fun refreshSessionToken() {
        sessionManager.renewToken()
    }


    // Additional ViewModel logic...
}

