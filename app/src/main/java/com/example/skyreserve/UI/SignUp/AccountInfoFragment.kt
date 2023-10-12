package com.example.skyreserve.UI.SignUp

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.databinding.FragmentSignUpAccountInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


/*
This is the user interface for signing up.
It includes UI components like EditTexts for username, password, first name, last name, and a button to trigger sign-up.
It observes the LiveData in SignUpViewModel to display the sign-up result.
*/
class AccountInfoFragment : Fragment(R.layout.fragment_sign_up_account_info) {
    private lateinit var authRepository: AuthRepository
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpAccountInfoBinding

    //private var success = false
    private var emailAddress: String? = null
    private var password: String? = null

    private var success: Unit? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val navController = findNavController(R.id.accountInfoNavHostFragment)
        //val navController = parentFragment?.findNavController()?.navigate(R.id.navHostFragment)
        //val navController = findNavController()



        // Access authRepository from SignUpActivity
        authRepository = (activity as? SignUpActivity)?.authRepository!!

        signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory(authRepository))
            .get(SignUpViewModel::class.java)
        binding = FragmentSignUpAccountInfoBinding.bind(view)


        binding.signUpButton.setOnClickListener {
            binding.signUpButton.isEnabled = false

            emailAddress = binding.emailAddressEditText.text.toString()
            password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            binding.emailAddressEditText.isEnabled = false
            binding.passwordEditText.isEnabled = false
            binding.confirmPasswordEditText.isEnabled = false

            CoroutineScope(Dispatchers.IO).launch {
                // Perform the validation and obtain the result
                val isEmailValid = signUpViewModel.validateEmail(emailAddress!!)
                val isPasswordValid = signUpViewModel.validatePassword(password!!, confirmPassword)
                println("isEmailValid: $isEmailValid")
                println("isPasswordValid: $isPasswordValid")

                // If PersonalInfo is optional then call viewModel's SignUp() here and get boolean
                // SignUp would only take emailAddress and password
                // keep if statement for Enabling fields and button incase of sign up fail
                if (isEmailValid && isPasswordValid) {
                    success = signUpViewModel.signUp(emailAddress!!, password!!, "", "")
                    // change var success to val success
                    // success = signUpViewModel.signUp(emailAddress, password, "", "")
                } else {
                    // don't need success = false
                    binding.signUpButton.isEnabled = true
                    binding.emailAddressEditText.isEnabled = true
                    binding.passwordEditText.isEnabled = true
                    binding.confirmPasswordEditText.isEnabled = true
                }
            }

            // do this in next fragment
//            CoroutineScope(Dispatchers.IO).launch {
//                val success = signUpViewModel.signUp(emailAddress, password, firstName, lastName)
//                // Update UI or handle success/failure here
//            }

        }

        signUpViewModel.signUpResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Navigate to the dashboard or ask for more personal info(optional)
                // if sign up successful - maybe don't need to pass data (take going back into account)
                println("hi")
//                val action = AccountInfoFragmentDirections.actionAccountInfoFragmentToPersonalInfoFragment()
//                findNavController().navigate(action) // <-- this one works
                //navController.navigate(action)

                //parentFragment?.findNavController()?.navigate(R.id.accountInfoNavHostFragment)

                val personalInfoFragment = PersonalInfoFragment().apply {
                    // Pass the authRepository to the fragment
                    authRepository = this@AccountInfoFragment.authRepository
                }

//                val fragmentManager = requireActivity().supportFragmentManager // or childFragmentManager if in a child fragment
//                fragmentManager.popBackStack()

                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, personalInfoFragment)
                fragmentTransaction.addToBackStack(null) // Optional, allows the user to navigate back
                fragmentTransaction.commit()


                // THIS ONE IS TO PASS DATA - error: Type mismatch: inferred type is String? but String was expected
//                val action = AccountInfoFragmentDirections
//                    .actionAccountInfoFragmentToPersonalInfoFragment(
//                        emailAddress, // Pass your data here
//                        password,
//                        // Add other arguments as needed
//                    )
                //findNavController().navigate(action)
            }
        }
    }
}
