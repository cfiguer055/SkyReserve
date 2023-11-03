package com.example.skyreserve.UI.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skyreserve.Database.SkyReserveDatabase
import androidx.navigation.fragment.NavHostFragment
import com.example.skyreserve.R
import com.example.skyreserve.Repository.AuthRepository
import com.example.skyreserve.UI.Home.HomeActivity
import com.example.skyreserve.databinding.ActivitySignUpBinding


/*
The activity manages the SignUpFragment.
It sets up the navigation flow for the sign-up process.
You can include any additional logic for initializing the app here.
*/
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)

            // Clear all previous activities from the stack and start a new task
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
