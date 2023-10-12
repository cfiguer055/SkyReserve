package com.example.skyreserve.UI.SignUp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.UI.Home.HomeActivity
import com.example.skyreserve.UI.SignIn.SignInActivity
import com.example.skyreserve.databinding.FragmentSignUpPersonalInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalInfoFragment : Fragment(R.layout.fragment_sign_up_personal_info) {
    private lateinit var authRepository: AuthRepository
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpPersonalInfoBinding

    private var firstName: String? = null
    private var lastName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authRepository = (activity as SignUpActivity).authRepository

        signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory(authRepository))
            .get(SignUpViewModel::class.java)
        binding = FragmentSignUpPersonalInfoBinding.bind(view)

        binding.nextButton.setOnClickListener {
            binding.nextButton.isEnabled = false

            firstName = binding.firstNameEditText.text.toString()
            lastName = binding.lastNameEditText.text.toString()
            address = binding.addressEditText.text.toString()
            phoneNumber = binding.phoneNumberEditText.text.toString()

            binding.firstNameEditText.isEnabled = false
            binding.lastNameEditText.isEnabled = false
            binding.addressEditText.isEnabled = false
            binding.phoneNumberEditText.isEnabled = false

            // check if any fields aren't empty
            var tempFalse = false
            if (tempFalse) {
                CoroutineScope(Dispatchers.IO).launch {
                    // Perform validation
                    // empty textfields are fine - wrong filled in textfields are not fine
                    // maybe add address, state, zip code, etc
                    if (true) {

                    } else {
                        binding.nextButton.isEnabled = true
                        binding.firstNameEditText.isEnabled = true
                        binding.lastNameEditText.isEnabled = true
                        binding.addressEditText.isEnabled = true
                        binding.phoneNumberEditText.isEnabled = true
                    }
                }
            } else {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                //findNavController().navigateUp()
            }
        }
    }
}