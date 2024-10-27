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
import com.example.skyreserve.util.SignUpResult
import com.example.skyreserve.util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * EN:
 * Activity for managing the user sign-up process. It handles user input validation,
 * displays error messages, and navigates between the SignUp screen and other screens.
 * Initializes the necessary dependencies, like the AuthRepository and session manager.
 *
 * ES:
 * Actividad para gestionar el proceso de registro de usuarios. Maneja la validación de entradas
 * del usuario, muestra mensajes de error, y navega entre la pantalla de registro y otros pantallas.
 * Inicializa los dependencias necesarios, como el AuthRepository y gestor de sesiones.
 *
 * IT:
 * Atività per gestire il processo di registrazione degli utenti. Gestisce la validazione dell'input
 * dell'utente, visualizza messaggi di errore e naviga tra la schermata di registrazione e altre schermate.
 * Inizializza le dipendeze necessarie, come AuthRepository e il gestore della sessione.
 *
 * */
@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var sessionManager: LocalSessionManager

    @Inject
    lateinit var logger: UserInteractionLogger

    private lateinit var binding: ActivitySignUpBinding
    //private lateinit var signUpViewModel: SignUpViewModel

    private val authViewModel: AuthViewModel by viewModels()
    // private lateinit var userViewModel: UserViewModel


    /**
     * EN:
     * Called when the activity is first created. Sets up the view binding, initializes
     * the sign-up UI elements, and sets up listeners for user input changes and button clicks.
     *
     * ES:
     * Se llama cuando se crea la actividad por primera vez. Configura la vinculación de vistas,
     * inicializa los elementos de la interfaz de usuarios de registro y configura los listeners
     * para cambios en la entrada del usuario y clics en botones.
     *
     * IT:
     * Viene chiamato quando l'atività viene creata per la prima volta. Configura il binding delle viste,
     * inizializza gli elementi dell'interfaccia di registrazione e imposta i listener
     * per i cambiamenti dell'input dell'utente e i click sui pulsanti.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val userAccountDao = (application as MyApp).userAccountDao
        //authRepository = AuthRepository(userAccountDao) // Create an instance of AuthRepository with the required dependencies
        //signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory(authRepository, this))[SignUpViewModel::class.java]
        //val sessionManager = LocalSessionManager(this)
        // userViewModel = ViewModelProvider(this, UserViewModelFactory(authRepository, sessionManager, this))[UserViewModel::class.java]



        // EN: Set up listeners for text changes in email, password, and confirm password fields
        // ES: Configurar listeners para cambios de texto en los campos de correo, contraseña y confirmación de contraseña
        // IT: Impostare i listener per cambiamenti di testo nei campi email, password e conferma password
        binding.emailEditText.addTextChangedListener { resetInputUI() }
        binding.passwordEditText.addTextChangedListener { resetInputUI() }
        binding.confirmPasswordEditText.addTextChangedListener { resetInputUI() }

        // EN: Display password requirements when the password field gains focus
        // ES: Mostrar los requisitos de contraseña cuando el campo de contraseña tiene foco
        // IT: Visualizzare i requisiti della password quando il campi della password è attivo
        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // User has focused on the password field
                displayPasswordRequirements()
            } else {
                // User has defocused the password field
                hidePasswordRequirements()
            }
        }

        // EN: Set the sign-up button click listener to initiate the sign-up process
        // ES: Configurar el listener del botón de registro para iniciar el proceso de registro
        // IT: Impostare il listener del pulsante di registrazione per avviare il processo di registrazione
        binding.signUpButton.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            try {
                disableSignUpUI()

                authViewModel.signUp(email, password, confirmPassword)
                observeSignUpResult()
            } catch (e: Exception) {
                Log.e("SignUpActivity", "Error during sign up", e)
            } finally {
                enableSignUpUI()
            }
        }

        // EN: Set click listener for the "Sign in" link
        // ES: Configurar el listener del enlace "Iniciar Session"
        // IT: Impostare il listener per il link "Accedi"
        binding.signInLinkTextView.setOnClickListener {
            navigateToSignIn()
        }
    }

    /**
     * EN:
     * Resets the UI for the input fields, hiding any error messages and restoring the default
     * appearance of the input fields.
     *
     * ES:
     * Restablece la interfaz de usuario para los campos de entrada, ocultando los mensajes de error
     * y restaurando la aparencia predeterminada de los campos de entrada.
     *
     * IT:
     * Reimposta l'interfaccia utente per i campi di input, nascondendo eventuali messaggi di errore
     * e ripristiando l'aspetto predefinito dei campi di input.
     *
     * */
    private fun resetInputUI() {
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""

        // Reset the EditText and TextView to their default colors
        binding.emailEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_background)
        binding.confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_background)
    }

    /**
     * EN:
     * Enables all sign-up UI elements, allowing the user to input their information.
     *
     * ES:
     * Habilita todos los elementos de la interfaz de usuario de registro, permetiendo al usuario
     * ingresar su información.
     *
     * IT:
     * Abilita tutti gli elementi dell'interfaccia di registrazione, consentendo all'utente
     * di inserire le proprie informazioni.
     *
     * */
    private fun enableSignUpUI() {
        binding.emailEditText.isEnabled = true
        binding.passwordEditText.isEnabled = true
        binding.confirmPasswordEditText.isEnabled = true
        binding.signUpButton.isEnabled = true
    }

    /**
     * EN:
     * Disables all sign-up UI elements to prevent further input during the sign-up process.
     *
     * ES:
     * Deshabilita todos los elementos de la interfaz de registro para evitar más entradas durante
     * el proceso de registro.
     *
     * IT:
     *
     *
     * */
    private fun disableSignUpUI() {
        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
        binding.confirmPasswordEditText.isEnabled = false
        binding.signUpButton.isEnabled = false
    }

    /**
     * EN:
     * Displays the password requirements on the UI when the user focuses on the password field.
     *
     * ES:
     * Muestra los requisitos de la contraseña en la interfaz cuando el usuario enfoca el campo
     * de la contraseña.
     *
     * IT:
     *
     *
     * */
    fun displayPasswordRequirements() {
        binding.passwordRequirementsTextView.visibility = View.VISIBLE
    }

    /**
     * EN:
     * Hides the password requirements from the UI when the password field is no longer focused.
     *
     * ES:
     * Oculta los requisitos de la contraseña en la interfaz cuando el campo de contraseña ya no
     * tiene foco.
     *
     * IT:
     *
     *
     * */
    fun hidePasswordRequirements() {
        binding.passwordRequirementsTextView.visibility = View.GONE
    }

    /**
     * EN:
     * Navigates to the HomeActivity after a successful sign-up. Flags are used to clear the
     * task stack and make HomeActivity the root of the new task.
     *
     * ES:
     * Navega a HomeActivity después de un registro exitoso. Se usan flags para limpiar la pila de
     * tareas y hacer de HomeActivity la raíz de la nueva tarea.
     *
     * IT:
     *
     *
     * */
    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("FROM_SIGN_UP", true)
        startActivity(intent)
        // finish() temp
    }

    /**
     * EN:
     * Navigates to the SignInActivity when the user clicks the "Sign In" link.
     * The activity is reordered to the front of the task stack.
     *
     * ES:
     * Navega a SignInActivity cuando el usuario hace clic en el enlace "Iniciar Sesión".
     * El actividad se reordena al frente de la pila de tareas.
     *
     * IT:
     *
     *
     * */
    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

        startActivity(intent)
    }

    /**
     * EN:
     * Observes the sign-up result from the ViewModel and handles different outcomes, such as success,
     * missing information, or invalid input. Displays appropriate error messages or navigates to HomeActivity.
     *
     * ES:
     * Observa los resultados del registro en el ViewModel y maneja diferentes escenarios, como éxito,
     * información faltante o entrada inválida. Muestra mensajes de error apropiados o navega a HomeActivity.
     *
     * IT:
     *
     *
     * */
    private fun observeSignUpResult() {
        authViewModel.signUpResult.observe(this) { result ->
            when (result) {
                SignUpResult.SUCCESS -> navigateToHome()
                SignUpResult.MISSING_INFORMATION -> showSignUpError("Please fill out all fields.", "MISSING_INFORMATION")
                SignUpResult.INVALID_EMAIL -> showSignUpError("Invalid email format.", "INVALID_EMAIL")
                SignUpResult.EXISTING_EMAIL -> showSignUpError("This email is already taken. Try another.", "INVALID_EMAIL")
                SignUpResult.SHORT_PASSWORD -> showSignUpError("Your password is too short. It needs to be at least 8 characters.", "INVALID_PASSWORD")
                SignUpResult.MISSING_CAPITAL_LETTER -> showSignUpError("Add at least one capital letter in your password.", "INVALID_PASSWORD")
                SignUpResult.MISSING_LOWCASE_LETTER -> showSignUpError("Add at least one lowercase letter in your password.", "INVALID_PASSWORD")
                SignUpResult.MISSING_DIGIT -> showSignUpError("Include at least one number in your password.", "INVALID_PASSWORD")
                SignUpResult.PASSWORD_NO_MATCH -> showSignUpError("Oops! Your passwords do not match.", "PASSWORD_NO_MATCH")
                SignUpResult.TERMS_NOT_ACCEPTED -> showSignUpError("You must accept the terms and conditions to continue.", "TERMS_NOT_ACCEPTED")
                SignUpResult.NETWORK_ERROR -> showSignUpError("Check your internet connection and try again.", "NETWORK_ERROR")
                SignUpResult.UNKNOWN_ERROR -> showSignUpError("Something went wrong. Please try again later.", "UNKNOWN_ERROR")
            }
        }
    }

    /**
     * EN:
     * Displays a specific error message in the UI and highlights the problematic fields based on
     * the provided error code.
     *
     * ES:
     * Muestra un mensaje de error específico en la interfaz de usuario y resalta los campos
     * problemáticos según el código de error proporcionado.
     *
     * IT:
     *
     *
     * */
    private fun showSignUpError(message: String, errorCode: String) {
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = message

        when (errorCode) {
            "MISSING_INFORMATION" -> {
                // EN: Check each EditText and if empty, change the background
                // ES: Comprueba cada EditText y si es vacío, cambia el fondo
                // IT:
                if (binding.emailEditText.text.isNullOrEmpty()) {
                    binding.emailEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                }
                if (binding.passwordEditText.text.isNullOrEmpty()) {
                    binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                }
                if (binding.confirmPasswordEditText.text.isNullOrEmpty()) {
                    binding.confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                }
            }
            "INVALID_EMAIL" -> {
                // Handle INVALID_EMAIL error
                binding.emailEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            "INVALID_PASSWORD" -> {
                // This case can handle multiple password-related errors
                // Handle INVALID_PASSWORD error
                binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            "PASSWORD_NO_MATCH" -> {
                // Handle PASSWORD_NO_MATCH error
                binding.passwordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
                binding.confirmPasswordEditText.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            "TERMS_NOT_ACCEPTED" -> {
                // Handle TERMS_NOT_ACCEPTED error
            }
            "NETWORK_ERROR" -> {
                // Handle NETWORK_ERROR error
            }
            else -> {
                // Optionally, handle any other unexpected error codes
            }
        }
    }
}
