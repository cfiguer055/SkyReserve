package com.example.skyreserve.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.skyreserve.R
import com.example.skyreserve.ui.account.AccountActivity
import com.example.skyreserve.ui.flightSearch.FlightSearchActivity
import com.example.skyreserve.util.AirportsData
import com.example.skyreserve.util.UserData
import com.example.skyreserve.util.UserInteractionLogger
import com.example.skyreserve.databinding.ActivityHomeBinding
import com.example.skyreserve.databinding.DialogAirportAutoCompleteBinding
import com.example.skyreserve.databinding.DialogSignUpBinding
import java.text.SimpleDateFormat
import com.example.skyreserve.repository.UserAccountRepository
import com.example.skyreserve.ui.authentication.AuthViewModel
import com.example.skyreserve.ui.account.UserAccountViewModel
import com.example.skyreserve.util.LocalSessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


/**
 * ES:
 * Actividad principal para la pantalla de inicio de la aplicación. Maneja interacciones
 * de usuario, búsqueda de vuelos y configuración de perfil. Esta actividad sirve como punto de
 * entrada para la experiencia del usuario.
 *
 * @property
 *
 * -----------------------
 *
 * IT:
 * Attivitá principale per la schermata iniziale dell'applicazione. Gestisce le
 * interazioni dell'utente, la rivera di voli e la configurazione del profilo. Questa activitá
 * funge da punto di ingresso per l'esperienza utente.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    // ES: Este es el código de solicitud que utilizarás al iniciar el FlightSearchActivity
    // IT: Questo é il codice di richiesta che utilizzarai quando lanci FlightSearchAcvitivy

    // ES: Esto puede ser cualquier entero único para a la actividad
    // IT: Questo puó essere un intero qualsiasi unico per l'Attivitá
    private val FLIGHT_SEARCH_REQUEST_CODE = 1

    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var sessionManager: LocalSessionManager

    @Inject
    lateinit var userAccountRepository: UserAccountRepository

    @Inject
    lateinit var logger: UserInteractionLogger

    private val authViewModel: AuthViewModel by viewModels()
    private val userAccountViewModel: UserAccountViewModel by viewModels()

    private var passengerCount = 1
    private val maxPassengerCount = 9

    private lateinit var email: String

    /**
     * Registers a result handler for [FlightSearchActivity] that processes the user's flight search
     * selections and updates the UI accordingly.
     *
     * ES:
     * Registra un manejador de resultado para [FlightSearchActivity] que procesa las selecciones de
     * búsqueda de vuelo del usuario y actualiza la interfaz de usuario en consecencia.
     *
     * IT:
     * Registra un gestore di risultati per [FlightSearchActivity] che elabora le selezioni di ricerca
     * del volo dell'utenete e aggiorna di conseguenza l'interfaccia utente.
     *
     * */
    private val startFlightSearchActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // ES: Manejar los datos de FlightSearchActivity
            // IT: Gestire i dati da FlightSearchActivity

            val data = result.data

            // ES: Usa los datos para obtener los extras y hacer algo con ellos
            // IT: Usa i dati per ottenere gli extra e fare qualcosa con essi
            val tripType = data?.getStringExtra("ROUND_TRIP")
            val departAirport = data?.getStringExtra("DEPART_AIRPORT")

            // ... ES: y así con otros extras
            // ... IT: e cosí via con altri extra
        }
    }


    /**
     * Called when the activity is first created. Sets up the layout, initializes session data,
     * and handles user interaction for the home screen.
     *
     * ES:
     * Se llama cuando se crea la actividad por primera vez. Configura el disño. Inicializa los
     * datos de sesión, y gestiona la interacción del usuario para la pantalla principal.
     *
     * IT:
     * Viene chiamato quando l'attivitá viene creata per la prima volta. Configura il layout,
     * inizializza i dati di sessione e gestisce l'interazione dell'utente per la schermata principale.
     *
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize logger
        //logger = (applicationContext as MyApp).logger
        // Initialize sessionManager
        //sessionManager = (applicationContext as MyApp).sessionManager

        //val userAccountDao = (application as MyApp).userAccountDao
        // userAccountRepository = UserAccountRepository(application)
        //val sessionManager = LocalSessionManager(this)

        // userAccountViewModel = ViewModelProvider(this, HomeViewModelFactory(userAccountRepository, sessionManager, this))[UserAccountViewModel::class.java]


        val isNewUser = intent.getBooleanExtra("FROM_SIGN_UP", false)
        if (isNewUser) {
            showSignUpDialog()
        } else {
            fetchUserDetailsAfterSignIn()
        }

        // EN: Validate session and get user email
        // ES: Validar la sessión del usuario y recuperar el correo electronicó del usuario
        // IT: Convalidare la sessione utente e recuparare l'email dell'utente
        if (authViewModel.validateSession()) {

            // EN: Use userEmail to fetch user details if needed
            // ES: Usar userEmail para obtener los detalles del usuario si es necesario
            // IT: Usare userEmail per recuperare i dettagli dell'utente, se necessario
            email = sessionManager.getUserEmail().toString()
            userAccountViewModel.fetchUserDetails(email)

            userAccountViewModel.userDetails.observe(this) { userData  ->
                binding.nameText.text = userData?.firstName ?: ""
            }

        } else {
            // EN: Handle invalid session, e.g., navigate to login
            // ES: Manejar sessión invalida limpiando la interfaz de usuario
            // IT: Gestire sessione non valida cancellado l'interfaccia utente
            email = ""
            binding.nameText.text = ""
        }

        setupBottomNavigation()

        // logger = (application as MyApp).logger
        //logger.logInteraction("HomeActivity:")

        binding.oneWayRadioButton.setOnClickListener {
            // EN: Handle one-way trip radio button click
            // ES: Manejar clic en el botón de opción de viaje de ida
            // IT: Gestire il clic sul pulsante di opzione per viaggio di sola andata

            //logger.logInteraction("Button clicked: ${binding.oneWayRadioButton.text}")
        }

        binding.departButton.setOnClickListener {
            // EN: Handle departure button click
            // ES: Manejar clic en el butón de salida
            // IT: Gestire il clic sul pulsante di partenza

            //logger.logInteraction("Button clicked: ${binding.departButton.id}")
            showAirportAutoCompleteDialog(true)
        }

        binding.arriveButton.setOnClickListener {
            // EN: Handle arrival button click
            // ES: Manejar clic en el butóon de llegada
            // IT: Gestire il clic sul pulsante di arrivo

            //logger.logInteraction("Button clicked: ${binding.arriveButton.id}")
            showAirportAutoCompleteDialog(false)
        }

        binding.departureDateEditText.setOnClickListener {
            // EN: Show window for date picker dialog for selecting departure date
            // ES: Mostrar cuadro de diálogo para seleccionar la fecha de salida
            // IT: Mostrare finestra di dialogo per selezionare la data di partenza

            //logger.logInteraction("Button clicked: ${binding.departureDateEditText.id}")
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
                    binding.departureDateEditText.setText(selectedDate)
                    //logger.logInteraction("Date Selected: ${binding.departureDateEditText.text}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.returnDateEditText.setOnClickListener {
            // EN: Show date picker dialog for selecting return date
            // ES: Mostrar cuadro de diálogo para seleccionar la fecha de regreso
            // IT: Mostare finestra di dialogo per selezionare la data di ritorno

            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth ${getMonthShortName(month)}"
                    binding.returnDateEditText.setText(selectedDate)
                    //logger.logInteraction("Button clicked: ${binding.returnDateEditText.text}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.passengerCountTextView.text = passengerCount.toString()

        binding.decrementPassengerButton.setOnClickListener {
            // EN: Decrease passenger count
            // ES: Disminuir el número de pasajeros
            // IT: Diminuire il numero di passeggeri

            //logger.logInteraction("Button clicked: Decrement(${binding.decrementPassengerButton.text})")
            if (passengerCount > 1) {
                passengerCount--
                binding.passengerCountTextView.text = passengerCount.toString()
            }
            //logger.logInteraction("Passenger Count: $passengerCount")
        }

        binding.incrementPassengerButton.setOnClickListener {
            // EN: Increase passenger count
            // ES: Aumentar el número de pasajeros
            // IT: Aumentare il numero di passeggeri

            //logger.logInteraction("Button clicked: Increment(${binding.incrementPassengerButton.text})")
            if (passengerCount < maxPassengerCount) {
                passengerCount++
                binding.passengerCountTextView.text = passengerCount.toString()
            }
            //logger.logInteraction("Passenger Count: $passengerCount")
        }

        binding.searchFlightButton.setOnClickListener {
            // EN: Initiate flight search
            // ES: Iniciar búsqueda de vuelos
            // IT: Avviare la ricerca di voli

            //logger.logInteraction("Button clicked: ${binding.searchFlightButton.text}")
            navigateToFlightSearch()
        }

    }


    /**
     * EN:
     * Navigates to the [FlightSearchActivity] with the current flight search parameters.
     *
     * ES:
     * Navega a [FlightSearchActivity] con los parámetros de búsqueda de vuelo actuales.
     *
     * IT:
     * Naviga a [FlightSearchActivity] con i parametri di riecera volo correnti.
     *
     */
    private fun navigateToFlightSearch() {
        val intent = Intent(this, FlightSearchActivity::class.java)
        // DONT UNCOMMENT intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        val tripType = when (selectedRadioButtonId) {
            R.id.oneWayRadioButton -> "One way"
            R.id.roundTripRadioButton -> "Round trip"
            else -> "Not selected"
        }
//        logger.logInteraction("Flight Search Fields")
//        logger.logInteraction("DEPART_AIRPORT : ${binding.departButton.text}")
//        logger.logInteraction("ARRIVE_AIRPORT : ${binding.arriveButton.text}")
//        logger.logInteraction("DEPART_DATE : ${binding.departureDateEditText.text}")
//        logger.logInteraction("ROUND_TRIP : $tripType")
//        logger.logInteraction("NUM_PASSENGERS : $passengerCount")
//        logger.logInteraction("Navigating To FlightSearchActivity")

        intent.putExtra("EXTRA_EMAIL", email)
        intent.putExtra("ROUND_TRIP", tripType)
        intent.putExtra("DEPART_AIRPORT", binding.departButton.text.toString())
        intent.putExtra("ARRIVE_AIRPORT", binding.arriveButton.text.toString())
        intent.putExtra("DEPART_DATE", binding.departureDateEditText.text.toString())
        //intent.putExtra("RETURN_DATE", binding.returnDateEditText.text.toString())
        intent.putExtra("NUM_PASSENGERS", passengerCount)

        startActivity(intent)
    }

    /**
     * EN:
     * Navigates to the [AccountActivity] to manage user account information.
     *
     * ES:
     * Navega a [AccountActivity] para gestionar la informatción de la cuenta del usuario.
     *
     * IT:
     * Naviga a [AccountActivity] per gestire le informazioni dell'account utente.
     *
     * */
    private fun navigateToAccount() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }



    /**
     * EN:
     * Retrieves the short name (abbreviation) of a month based on the given month index.
     * The index should be an integer between 0 (January) and 11 (December).
     * @param month The index of the month (0 for January, 11 for December).
     * @return A string representing the abbreviated name of the month.
     *
     * Español: Recupera el nombre corto (abreviatura) de un mes basado en el índice dado.
     * El índice debe ser un número entero entre 0 (enero) y 11 (diciembre).
     * @param month El indice del mes (0 para enero, 11 para diciembre).
     * @return Una cadena que representa el nombre abreviado del mes.
     *
     * Italiano: Recupera il nome abbreviato di un mese in base all'indice fornito.
     * L'indice deve essere un numero intero tra 0 (gennaio) e 11 (dicembre).
     * @param month L'indice del mese (0 per gennaio, 11 per dicembre).
     * @return Una stringa che rappresenta il nome abbreviato del mese.
     *
     */
    fun getMonthShortName(month: Int): String {
        val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        return dateFormat.format(calendar.time)
    }

    /**
     * EN:
     * Displays a dialog for the user to complete their profile information after signing up.
     *
     * ES:
     * Muestra cuadro de diálogo para que el usuario complete su información después de registrarse.
     *
     * IT:
     * Mostra una finestra di dialogo per consentire all'utente di completare le informazioni del
     * profilo dopo la registrazione.
     *
     * */
    private fun showSignUpDialog() {
        val binding = DialogSignUpBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("Complete Your Profile")
            setView(binding.root)
            setPositiveButton("Submit") { _, _ ->
                // Get the user's email from session manager

                //val userEmail = LocalSessionManager(this@HomeActivity).getUserEmail() ?: return@setPositiveButton

                email = userAccountViewModel.getUserEmail().toString()

                // Collect data from the UI
                val userData = UserData(
                    firstName = binding.firstNameEditText.text.toString(),
                    lastName = binding.lastNameEditText.text.toString(),
                    gender = binding.genderEditText.text.toString(),
                    phone = binding.phoneNumberEditText.text.toString(),
                    dateOfBirth = binding.dateOfBirthEditText.text.toString(),
                    address = binding.addressEditText.text.toString(),
                    stateCode = binding.stateCodeEditText.text.toString(),
                    countryCode = binding.countryCodeEditText.text.toString(),
                    passport = binding.passportEditText.text.toString()
                )

                // Pass data to ViewModel to handle the update
                // Account already created in SignUpActivity so update not insert
                userAccountViewModel.updateUserDetails(userData)

                // After updating, fetch user details to update UI
                fetchUserDetailsAfterSignIn()
            }
            setNegativeButton("Cancel", null)
        }.create()
        dialog.show()
    }

    private fun fetchUserDetailsAfterSignIn() {
        if (authViewModel.validateSession()) {
            email = userAccountViewModel.getUserEmail().toString()
            userAccountViewModel.fetchUserDetails(email)

            //Log.d("fetchUserDetailsAfterSignUp", "Valid Email")

            userAccountViewModel.userDetails.observe(this) { userData  ->
                binding.nameText.text = userData?.firstName ?: ""
            }
        } else {
            // Handle invalid session
            binding.nameText.text = ""
            //Log.d("fetchUserDetailsAfterSignUp", "Invalid Email")
        }
    }

    private fun showAirportAutoCompleteDialog(departure: Boolean) {

        // Inflate the layout for the dialog
        val binding = DialogAirportAutoCompleteBinding.inflate(layoutInflater)
        val searchEditText = binding.searchEditText
        val airportsListLayout = binding.airportsListLayout

        // Create the AlertDialog and set its view to our inflated layout
        val dialog = AlertDialog.Builder(this).apply {
            if (departure) setTitle("Departure Airport") else setTitle("Arrival Airport")
            searchEditText.hint = if (departure) "Enter departure airport" else "Enter arrival airport"
            setView(binding.root) // This sets the custom view for the dialog
            setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
        }.create()

        // Show the AlertDialog
        dialog.show()

        // Set up a text changed listener on the search EditText to filter the list as the user types
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val filteredAirports = filterAirports(s.toString(), AirportsData.airports)
                updateAirportsList(departure, dialog, airportsListLayout, filteredAirports)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    // Filter airports based on the search query
    fun filterAirports(query: String, airports: Array<String>): List<String> {
        return airports.filter {
            it.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        }
    }

    // Update the list of airports displayed in the ScrollView
    @SuppressLint("SetTextI18n")
    fun updateAirportsList(departure: Boolean, dialog: AlertDialog, layout: LinearLayout, airports: List<String>) {
        // Remove all views before adding the new filtered list
        layout.removeAllViews()

        // Add TextViews for each filtered airport
        for (airport in airports) {
            val textView = TextView(this)
            textView.text = airport
            textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textView.setPadding(16, 16, 16, 16) // for example
            textView.setOnClickListener {
                // Handle the airport selection here
                val parts = airport.split(" - ")
                val airportCode = parts.getOrNull(1) ?: "" // This will get "LAX" or an empty string if the part is not found.
                val city = parts.getOrNull(2)?.split(",")?.getOrNull(0) ?: "" // This will get "LOS ANGELES" or an empty string if the part is not found.
                if(departure) binding.departButton.text = "$airportCode - $city" else binding.arriveButton.text = "$airportCode - $city"
                //logger.logInteraction("Alert Dialog Button clicked: $airportCode - $city")

                dialog.dismiss()
            }
            layout.addView(textView)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home action
                    true
                }
                R.id.navigation_order -> {
                    // Handle order action
                    true
                }
                R.id.navigation_search -> {
                    // Handle search action
                    navigateToFlightSearch()
                    true
                }
                R.id.navigation_account -> {
                    // Handle account action
                    navigateToAccount()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        // Create an intent that mimics the Home button being pressed
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

}