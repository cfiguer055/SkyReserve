package com.example.skyreserve.UI.Welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skyreserve.R
import com.example.skyreserve.UI.SignIn.SignInActivity
import com.example.skyreserve.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment's layout
        val rootView = inflater.inflate(R.layout.fragment_welcome, container, false)

        // Initialize your views (signInBinding and signUpBinding) using rootView.findViewById
        binding = FragmentWelcomeBinding.bind(rootView)

        binding.signInButton.setOnClickListener {
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)

            // supposedly navcontroller is for fragment to fragment within same activity
            //findNavController().navigate(R.id.action_welcomeFragment_to_signInFragment)
        }

        binding.signUpButton.setOnClickListener {
            // Handle sign-up button click
        }

        return rootView
    }
}