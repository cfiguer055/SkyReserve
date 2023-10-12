package com.example.skyreserve.UI.Welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skyreserve.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Check if the FragmentContainer is empty before adding the Fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WelcomeFragment())
                .commit()
        }
    }
}