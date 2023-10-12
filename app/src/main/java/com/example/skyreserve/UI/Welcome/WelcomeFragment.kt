package com.example.skyreserve.UI.Welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skyreserve.R
import com.example.skyreserve.UI.SignIn.SignInActivity
import com.example.skyreserve.UI.SignUp.SignUpActivity
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
        }

        binding.signUpButton.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}