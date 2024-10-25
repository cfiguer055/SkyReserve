import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * EN:
 * Adapter for managing fragments in the WelcomeActivity's ViewPager. This adapter
 * takes a list of fragments and displays them based on the user's swipe gestures.
 *
 * ES:
 * Adaptador para gestionar fragmentos en el ViewPager de WelcomeActivity. Este adaptador
 * toma una lista de fragmentos y los muestra según los gestos de deslizamiento del usuario.
 *
 * IT:
 * Adattatore per gestire i frammenti nel ViewPager di WelcomeActivity. Questo adattatore
 * prende un elenco di frammenti e li visualizza in base ai gesti di scorrimento dell'utente.
 *
 */
class WelcomeViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    /**
     * EN: Returns the number of fragments to be displayed.
     *
     * ES: Devuelve el número de fragmentos que se mostrarán.
     *
     * IT: Restituisce il numero di frammenti da visualizzare.
     *
     * */
    override fun getItemCount(): Int {
        return fragments.size
    }

    /**
     * EN:
     * Creates and returns the fragment corresponding to the given position.
     *
     * ES: Crea y devuelve el fragmento correspondiente a la posición dada.
     *
     * IT: Crea e restituisce il fragmento corrispondente alla posizione data.
     *
     * */
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}