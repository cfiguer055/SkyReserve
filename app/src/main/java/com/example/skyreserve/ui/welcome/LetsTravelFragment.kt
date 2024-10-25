package com.example.skyreserve.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skyreserve.R

/**
 * EN:
 * Fragment that provides information about the final step of traveling. Part of the welcome
 * screen sequence.
 *
 * ES:
 * Fragmento que proporciona información sobre el último paso del viaje. Parte de la secuencia
 * de bienvenida.
 *
 * IT:
 * Frammenti che fornische informazioni sull'ultimo passo del viaggio. Parte della sequenza
 * di benvenudo.
 *
 * */
class LetsTravelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // EN: Inflate the layout for this fragment
        // ES: Inflar el diseño para este fragmento
        // IT: Espandere il layout per questo frammento
        return inflater.inflate(R.layout.fragment_lets_travel, container, false)
    }
}