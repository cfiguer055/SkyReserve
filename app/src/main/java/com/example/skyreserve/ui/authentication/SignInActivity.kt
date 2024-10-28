package com.example.skyreserve.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.skyreserve.R
import com.example.skyreserve.repository.AuthRepository
import com.example.skyreserve.ui.home.HomeActivity
import com.example.skyreserve.util.LocalSessionManager
import com.example.skyreserve.util.SignInResult
import com.example.skyreserve.util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * EN:
 * Activity for managing the user sign-in process. Handles user input validation,
 * displays error messages, and navigates between the SignIn screen and other screens.
 * Initializes necessary dependencies like the AuthRepository and session manager.
 *
 * ES:
 * Actividad para gestionar el proceso de inicio de sesión de usuarios. Maneja la validación
 * de entradas del usuario, muestra mensajes de error y navega entre la pantalla de inicio de
 * sesión y otras pantallas. Inicializa las dependecias necesarias como AuthRepository y el
 * gestor de sesión.
 *
 * IT:
 *
 *
 * */
@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var sessionManager: LocalSessionManager

    @Inject
    lateinit var logger: UserInteractionLogger


    private lateinit var binding: ActivitySignInBinding

    private val authViewModel: AuthViewModel by viewModels()
    // private lateinit var userViewModel: UserViewModel

    private lateinit var email: String

    /**
     * EN:
     * Called when the activity is first created. Sets up the view binding, initializes
     * the sign-in UI elements, and sets up listeners for user input changes and button clicks.
     *
     * ES:
     * Se llama cuando se crea la actividad por primera vez. Configura el vinculación de vistas,
     * inicializa los elementos de la interfaz de inicio de sesión y configura listeners para
     * cambios en la entrada del usuario y clics en botones.
     *
     * IT:
     *
     *
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val userAccountDao = (application as MyApp).userAccountDao
        //authRepository = AuthRepository(userAccountDao)
        // val sessionManager = LocalSessionManager(this)

        // userViewModel = ViewModelProvider(this, UserViewModelFactory(authRepository, sessionManager, this))[UserViewModel::class.java]
        // signInViewModel = ViewModelProvider(this, SignInViewModelFactory()).get(SignInViewModel::class.java)

        //logger = (application as MyApp).logger


        // EN: Set up listeners to reset the input UI when the user starts typing
        // ES: Configurar listeners para restablecer la interfaz de entrada cuando el usuario comienza a escribir
        // IT:
        binding.emailEditText.addTextChangedListener { resetInputUI() }
        binding.passwordEditText.addTextChangedListener { resetInputUI() }

        // EN: Set the sign-in button click listener to initiate the sign-in process
        // ES: Configurar el listener del botón de inicio de sesión para iniciar el proceso de inicio de sesión.
        // IT:
        binding.signInButton.setOnClickListener {
            email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            try {
                disableSignInUI()

//                signInViewModel.signIn(email, password) { result ->
//                    when (result) {
//                        SignInResult.SUCCESS -> navigateToHome()
//                        SignInResult.INVALID_CREDENTIALS -> showSignInError("Invalid credentials. Please try again.")
//                        SignInResult.NETWORK_ERROR -> showSignInError("Network error. Please check your internet connection.")
//                        SignInResult.UNKNOWN_ERROR -> showSignInError("An unknown error occurred.")
//                    }
//                }
                authViewModel.signIn(email, password)
                observeSignInResult()

            } catch (e: Exception) {
                Log.e("SignInActivity", "Error during sign in", e)
            } finally {
                enableSignInUI()
            }
        }

        // EN: Set click listener for signing in with Google
        // ES: Configurar el listener para iniciar sesión con Google
        // IT:
        binding.signInWithGoogle.setOnClickListener {
            email = "google@gmail.com"
            navigateToHome()
        }

        // EN: Set click listener for navigating to the sign-up screen
        // ES: Configurar el listener para navegar a la pantalla de registro
        // IT:
        binding.signUpLinkTextView.setOnClickListener {
            navigateToSignUp()
        }
    }

    /**
     * EN:
     * Resets the UI for the input fields, hiding any error messages and restoring the default
     * appearance of the input fields.
     *
     * ES:
     * Restablece la interfaz de usuario para los campos de entrada, ocultando los mensajes de error
     * y restaurando la appariencia predeterminada de los campos de entrada.
     *
     * IT:
     * Reimposta l'interfaccia utente per i campi di input, nascondende eventuali messaggi di errore
     * e ripristiando l'aspetto predefinito dei campi di input.
     *
     * */
    private fun resetInputUI() {
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""

        // Reset the EditText and TextView to their default colors
        binding.emailEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_background)
    }

    /**
     * EN:
     * Disables all sign-in UI elements to prevent further input during the sign-in process.
     *
     * ES:
     * Deshabilita todos los elementos de la interfaz de inicio de sesión para evitar entradas durante
     * el proceso de inicio de sesión.
     *
     * IT:
     *
     *
     * */
    private fun disableSignInUI() {
        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
        binding.signInButton.isEnabled = false
    }

    /**
     * EN:
     * Enables all sign-in UI elements, allowing the user to input their information.
     *
     * ES:
     * Habilita todos los elementos de la interfaz de usuario de inicio de sesión, permitiendo el
     * usuario a ingresar su información.
     *
     * IT:
     *
     *
     * */
    private fun enableSignInUI() {
        binding.emailEditText.isEnabled = true
        binding.passwordEditText.isEnabled = true
        binding.signInButton.isEnabled = true
    }

    /**
     * EN:
     * Navigates to the HomeActivity after a successful sign-in. Flags are used to clear the
     * task stack and make HomeActivity the root of the new task.
     *
     * ES:
     * Navega a HomeActivity después de un inicio de sesión exitoso. Se usa flags para limpiar
     * la pila de tareas y hacer de HomeActivity el raíz de la nueva tarea.
     *
     * IT:
     *
     *
     * */
    private fun navigateToHome() {
//        logger.init(email)
//        logger.logInteraction("Log Start: $email")
//        logger.logInteraction("Navigating to HomeActivity")

        val intent = Intent(this, HomeActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("EXTRA_EMAIL", email)
        startActivity(intent)
        // finish() temp
    }

    /**
     * EN:
     * Navigates to the SignUpActivity when the user clicks the "Sign Up" link.
     * The activity is reordered to the front of the task stack.
     *
     * ES:
     * Navega a SignUpActivity cuando el usuario hace clic en el enlace "Registrar".
     * La actividad se reordena al frente de la pila de tareas.
     *
     * IT:
     *
     *
     * */
    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

        startActivity(intent)
    }

    /**
     * EN:
     * Navigates to the Forgot Password screen to allow the user to reset their password.
     * This function currently serves as a placeholder for future implementation.
     *
     * ES:
     * Navega a la pantalla "Olivdé mi constraseña" para permitir que el usario restablezca su
     * contraseña. Esta función actualmente actúa como un marcador de posición para una futura
     * implementación
     *
     * IT:
     *
     *
     * */
    private fun navigateToForgotPassword() {

    }

    /**
     * EN:
     * Observes the sign-in result from the ViewModel and handles different outcomes, such as success,
     * invalid credentials, or network errors. Displays appropriate error messages or navigates to HomeActivity.
     *
     * ES:
     * Observa el resultado del inicio de sesión en el ViewModel y maneja diferente escenarios, como éxito,
     * credenciales inválidas o errores de red. Muestra mesnajes de error apropiados o navega a HomeActivity
     *
     * IT:
     *
     *
     * */
    private fun observeSignInResult() {
        authViewModel.signInResult.observe(this) { result ->
            when (result) {
                SignInResult.SUCCESS -> navigateToHome()
                SignInResult.INVALID_CREDENTIALS -> showSignInError("Invalid credentials. Please try again.")
                SignInResult.NETWORK_ERROR -> showSignInError("Network error. Please check your internet connection.")
                SignInResult.UNKNOWN_ERROR -> showSignInError("An unknown error occurred.")
            }
        }
    }

    /**
     * EN:
     * Displays a specific error message in the UI and highlights the problematic fields based on
     * invalid input.
     *
     * ES:
     * Muestra un mensaje de error específico en la interfaz de usuario y resalta los campos
     * problemáticos sugún las entradas invalidas.
     *
     * IT:
     *
     *
     * */
    private fun showSignInError(message: String) {
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = message

        // Change the EditText background to indicate error
        binding.emailEditText.setBackgroundResource(R.drawable.edit_text_error_background)
        binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)

        // Show the error message to the user, e.g., using a Toast or a Snackbar
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
