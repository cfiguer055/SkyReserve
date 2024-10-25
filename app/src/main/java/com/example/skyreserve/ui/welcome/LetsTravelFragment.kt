package com.example.skyreserve.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skyreserve.R

/**
 * EN:
 * Fragment that guides the user on how to plan a trip. Part of the welcome screen sequence.
 *
 * ES:
 *
 *
 * IT:
 *
 *
 * */
class LetsTravelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // EN: Inflate the layout for this fragment
        // ES:
        // IT:
        return inflater.inflate(R.layout.fragment_lets_travel, container, false)
    }
}