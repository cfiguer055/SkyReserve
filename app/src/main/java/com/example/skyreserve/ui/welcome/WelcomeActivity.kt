package com.example.skyreserve.ui.welcome

import WelcomeFragment
import WelcomeViewPagerAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.example.skyreserve.ui.authentication.SignInActivity
import com.example.skyreserve.ui.authentication.SignUpActivity
import com.example.skyreserve.databinding.ActivityWelcomeBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * EN:
 * Activity that represents the welcome screen. It introduces the user to the app and
 * provides options to either sign up or sign in. Displays a set of fragments to explain
 * the app's main features.
 *
 * ES:
 * Actividad que representa la pantalla de bienvendia. Introduce al usuario a la applicación y
 * proporciona opciones para registrarse o iniciar sesión. Muestra un conjunto de fragmentos
 * para explicar las características principales de la aplicación.
 *
 * IT:
 * Atività che rappresenta la schermata di benvenuto. Introduce l'utente all'app e
 * fornisce opzioni per registrarsi o accedere. Visualizza una serie de frammenti
 * per spiegare le principali funzionalità dell'app.
 *
 * */
class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    /**
     * EN:
     * Called when the activity is first created. Sets up the view, fragments, and TabLayout for
     * the welcome screen. Configures navigation to the SignUpActivity and SignInActivity.
     *
     * ES:
     * Se llama cuando la actividad se crea por primera vez. Configura la vista, fragmentos, y el
     * TabLayout para la pantalla de bienvenida. Configura la navegación a SignUpActivity y SignInActivity.
     *
     * IT:
     * Viene chiamato quando l'atività viene creata per la prima volta. Configura la vista, i frammenti
     * e il TabLayout per la schermata di benvenuto. Configura la navigazione a SignUpActivity e SignInActivity.
     *
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EN: Initialize the fragments for the ViewPager
        // ES: Inicializar los fragmentos para el ViewPager
        // IT: Inizializzare i fragmenti per il ViewPager
        val fragments = listOf(
            WelcomeFragment(),
            PlanATripFragment(),
            BookTheFlightFragment(),
            LetsTravelFragment()
        )

        // EN: Set the adapter for the ViewPager and link it to the TabLayout
        // ES: Establecer el adaptador para el ViewPager y vincularlo con el TabLayout
        // IT: Impostare l'adattatore per il ViewPager e collegarlo al TabLayout
        val adapter = WelcomeViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
        binding.welcomeViewPager.adapter = adapter

        TabLayoutMediator(binding.tabDots, binding.welcomeViewPager) { tab, position ->
            // EN: Customize tab titles here
            // ES: Personaliza los titulos de pestañas aquí
            // IT: Personalizza i titoli delle schede qui
            when (position) {
                0 -> tab.text = ""
                1 -> tab.text = "Plan A Trip"
                2 -> tab.text = "Book The Flight"
                3 -> tab.text = "Let's Travel"
            }
        }.attach()

        // EN: Adjust the margins for the tab indicators
        // ES: Ajustar los márgenes para los indicadores de las pestañas
        // IT: Regolare i margini per gli indicatori delle schede
        adjustTabMargins()


        // EN: Set click listeners for navigation to SignUpActivity and SignInActivity
        // ES: Configurar listeners para la navegación a SignUpActivity y SignInActivity
        // IT: Impostare i listener per la navigazione a SignUpActivity e SignInActivity

        binding.getStartedButton.setOnClickListener {
            navigateToSignUp()
        }

        binding.signInLinkTextView.setOnClickListener {
            navigateToSignIn()
        }
    }


    /**
     * EN:
     * Adjusts the margins for the dots in the TabLayout to improve their appearance.
     *
     * ES:
     * Ajusta los márgenes de los puntos en el TabLayout para mejorar su apariencia.
     *
     * IT:
     * Regola i margini dei punti nel TabLayout per migliorare il loro aspetto
     *
     * */
    private fun adjustTabMargins() {
        val tabLayout = binding.tabDots
        tabLayout.post {
            for (i in 0 until tabLayout.tabCount) {
                val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
                val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(4.dpToPx(this), 0, 4.dpToPx(this), 0)
                tab.requestLayout()
            }
        }
    }

    /**
     * EN:
     * Navigates to the SignUpActivity to allow the user to create a new account.
     *
     * ES:
     * Navega a SignUpActivity para permitir al usuario crear una nueva cuenta.
     *
     * IT:
     * Naviga a SignUpActivity per consentire all'utente di creare un nuovo account.
     *
     * */
    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra("parentActivity", "WelcomeActivity")
        startActivity(intent)
    }

    /**
     * EN:
     * Navigates to the SignInActivity to allow the user to sign in with an existing account.
     *
     * ES:
     * Navega a SignInActivity para permitir al usuario iniciar sesión con una cuenta existente.
     *
     * IT:
     * Naviga a SignInActivity per consentire all'utente di accedere con un account esistente.
     *
     * */
    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    /**
     * EN:
     * Converts density-independent pixels (dp) to pixels (px) based on the device's screen density.
     * @param context The context of the current activity.
     * @return The corresponding pixel value.
     *
     * ES:
     * Convierte píxeles independientes de la densidad (dp) a píxeles (px), según la densidad de la
     * pantalla del dispositivo.
     * @param context El contexto de la actividad actual.
     * @return El valor correspondiente en píxeles.
     *
     * IT:
     * Converte i pixel indipendenti dalla densità (dp) in pixel (px) in base alla densità dello
     * schermo del dispositivo.
     * @param context Il contesto dell'attività corrente.
     * @return Il valore corrispondente in pixel.
     *
     * */
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}