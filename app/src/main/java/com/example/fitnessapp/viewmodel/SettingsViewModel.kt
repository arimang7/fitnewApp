package com.example.fitnessapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fitnessapp.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _weeklyGoal = MutableStateFlow(repository.getWeeklyGoal())
    val weeklyGoal: StateFlow<Int> = _weeklyGoal.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(repository.getNotificationsEnabled())
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    fun updateWeeklyGoal(goal: Int) {
        repository.setWeeklyGoal(goal)
        _weeklyGoal.value = goal
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)
        _notificationsEnabled.value = enabled
    }
}
