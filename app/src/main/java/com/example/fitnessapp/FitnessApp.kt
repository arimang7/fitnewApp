package com.example.fitnessapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize things here if needed
    }
}
