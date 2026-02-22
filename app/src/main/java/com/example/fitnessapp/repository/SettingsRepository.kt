package com.example.fitnessapp.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)

    fun getWeeklyGoal(): Int = prefs.getInt(KEY_WEEKLY_GOAL, 3) // Default 3 times a week
    
    fun setWeeklyGoal(goal: Int) {
        prefs.edit().putInt(KEY_WEEKLY_GOAL, goal).apply()
    }

    fun getNotificationsEnabled(): Boolean = prefs.getBoolean(KEY_NOTIFICATIONS, true)

    fun setNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply()
    }

    companion object {
        private const val KEY_WEEKLY_GOAL = "weekly_goal"
        private const val KEY_NOTIFICATIONS = "notifications"
    }
}
