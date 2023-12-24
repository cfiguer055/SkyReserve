package com.example.skyreserve.UI.Welcome

import WelcomeFragment
import WelcomeViewPagerAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.example.skyreserve.R
import com.example.skyreserve.UI.SignIn.SignInActivity
import com.example.skyreserve.UI.SignUp.SignUpActivity
import com.example.skyreserve.databinding.ActivityWelcomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = listOf(
            WelcomeFragment(),
            PlanATripFragment(),
            BookTheFlightFragment(),
            LetsTravelFragment()
        )

        val adapter = WelcomeViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
        binding.welcomeViewPager.adapter = adapter

        TabLayoutMediator(binding.tabDots, binding.welcomeViewPager) { tab, position ->
            // Customize tab titles here
            when (position) {
                0 -> tab.text = ""
                1 -> tab.text = "Plan A Trip"
                2 -> tab.text = "Book The Flight"
                3 -> tab.text = "Let's Travel"
            }
        }.attach()
        adjustTabMargins()

        binding.getStartedButton.setOnClickListener {
            navigateToSignUp()
        }

        binding.signInLinkTextView.setOnClickListener {
            navigateToSingIn()
        }
    }

    private fun adjustTabMargins() {
        val tabLayout = binding.tabDots
        tabLayout.post {
            for (i in 0 until tabLayout.tabCount) {
                val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
                val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(4.dpToPx(this), 0, 4.dpToPx(this), 0)
                tab.requestLayout()
            }
        }
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSingIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}