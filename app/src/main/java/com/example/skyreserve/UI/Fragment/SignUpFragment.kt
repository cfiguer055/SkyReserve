package com.example.skyreserve.UI.Fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.skyreserve.R
import com.example.skyreserve.UI.ViewModel.SignUpViewModel
import com.example.skyreserve.databinding.FragmentSignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
This is the user interface for signing up.
It includes UI components like EditTexts for username, password, first name, last name, and a button to trigger sign-up.
It observes the LiveData in SignUpViewModel to display the sign-up result.
*/
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = FragmentSignUpBinding.bind(view)

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val success = signUpViewModel.signUp(username, password, firstName, lastName)
                // Update UI or handle success/failure here
            }
        }

        signUpViewModel.signUpResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Navigate to the dashboard or ask for more personal info(optional)
            } else {
                // Display an error message
            }
        }
    }
}
