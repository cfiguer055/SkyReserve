import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skyreserve.R

/**
 * EN:
 * Fragment that serves as the introductory screen of the welcome sequence.
 * Displays a welcoming message to the user.
 *
 * ES:
 * Fragmento que sirve como la pantalla introductoria de la secuencia de bienvenida.
 * Muestra un mensaje de bienvenida al usuario.
 *
 * IT:
 * Frammenti che funge da schermata introduttiva della sequenza di benvenudo.
 * Visualizza un messaggio di benvenuto all'utente.
 *
 * */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // EN: Inflate the layout for this fragment
        // ES: Inflar el diseño para este fragmento
        // IT: Espandere il layout per questo frammento
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }
}
