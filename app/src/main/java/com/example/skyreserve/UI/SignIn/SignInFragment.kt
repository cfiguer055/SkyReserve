package com.example.skyreserve.UI.SignIn

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.R
import com.example.skyreserve.databinding.FragmentSignInBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.skyreserve.Repository.AuthRepository

//import kotlinx.android.synthetic.main.fragment_sign_in.*

/*
This is the user interface for signing in.
It includes UI components like EditTexts for username and password, and a button to trigger sign-in.
It observes the LiveData in SignInViewModel to display the sign-in result.
 */
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var authRepository: AuthRepository
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var binding: FragmentSignInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access authRepository from SignInActivity
        authRepository = (activity as SignInActivity).authRepository
        // Initialize your ViewModel using SignInViewModelFactory
        signInViewModel = ViewModelProvider(this, SignInViewModelFactory(authRepository))
            .get(SignInViewModel::class.java)
        binding = FragmentSignInBinding.bind(view)

        binding.signInButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Call signIn from a coroutine scope
            CoroutineScope(Dispatchers.IO).launch {
                val success = signInViewModel.signIn(username, password)
                // Update UI or handle success/failure here
            }
        }

        signInViewModel.signInResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Navigate to the dashboard
                val navController = findNavController()
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            } else {
                // Display an error message
            }
        }
    }
}
