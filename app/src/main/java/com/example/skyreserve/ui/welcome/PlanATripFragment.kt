package com.example.skyreserve.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skyreserve.R

/**
 * EN: Fragment that guides the user on how to plan a trip. Part of the welcome screen sequence.
 *
 * ES: Fragmento que guía al usuario sobre cómo planificar un viaje. Parte de la secuencia de bienvenida.
 *
 * IT: Frammenti che guida l'utente su come pianificare un viaggio. Parte della sequenza di benvenudo.
 *
 * */
class PlanATripFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // EN: Inflate the layout for this fragment
        // ES: Inflar el diseño para este fragmento
        // IT: Espandere il layout per questo frammento
        return inflater.inflate(R.layout.fragment_plan_a_trip, container, false)
    }
}