package com.example.skyreserve.UI.Welcome

import androidx.lifecycle.ViewModel
import com.example.skyreserve.R

class WelcomeViewModel : ViewModel() {
    // This could be replaced by LiveData or StateFlow if you need to observe changes.
    val images = listOf(
        R.drawable.welcome_main_logo,
        R.drawable.welcome_plan_logo,
        R.drawable.welcome_book_logo,
        R.drawable.welcome_travel_logo
    )

    // Any other logic related to the images or the welcome process would go here.
}