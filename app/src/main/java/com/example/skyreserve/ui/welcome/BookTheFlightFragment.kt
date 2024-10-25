package com.example.skyreserve.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skyreserve.R

/**
 * EN:
 * Fragment that explains the process of booking a flight. Part of the welcome screen sequence.
 *
 * ES:
 * Fragmento que explica el proceso de reservar un vuelo. Parte de la secuencia de bienvenida.
 *
 * IT:
 * Frammento che spiega il processo di prenotazione di un volo. Parte della sequenza di benvenuto
 *
 * */
class BookTheFlightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // EN: Inflate the layout for this fragment
        // ES: Inflar el diseño para este fragmento
        // IT: Espandere il layout per questo frammento
        return inflater.inflate(R.layout.fragment_book_the_flight, container, false)
    }
}