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
 *
 *
 * IT:
 *
 *
 * */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // EN: Inflate the layout for this fragment
        // ES:
        // IT:
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }
}
