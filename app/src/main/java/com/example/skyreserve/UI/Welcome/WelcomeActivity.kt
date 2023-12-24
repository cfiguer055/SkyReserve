package com.example.skyreserve.UI.Welcome

import WelcomeViewPagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.skyreserve.R
import com.example.skyreserve.UI.SignIn.SignInActivity
import com.example.skyreserve.UI.SignUp.SignUpActivity
import com.example.skyreserve.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = listOf(
            R.drawable.welcome_main_logo,
            R.drawable.welcome_plan_logo,
            R.drawable.welcome_book_logo,
            R.drawable.welcome_travel_logo
        )

        val adapter = WelcomeViewPagerAdapter(images)
        binding.welcomeViewPager.adapter = adapter

        binding.nextButton.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}