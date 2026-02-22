package com.example.fitnessapp.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.data.BloodPressureEntity
import com.example.fitnessapp.data.WorkoutEntity
import com.example.fitnessapp.repository.GeminiRepository
import com.example.fitnessapp.repository.HealthConnectManager
import com.example.fitnessapp.repository.StepCounterManager
import com.example.fitnessapp.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    private val healthConnectManager: HealthConnectManager,
    private val stepCounterManager: StepCounterManager,
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    val allWorkouts: StateFlow<List<WorkoutEntity>> = repository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentWorkout = MutableStateFlow<WorkoutEntity?>(null)
    val currentWorkout: StateFlow<WorkoutEntity?> = _currentWorkout.asStateFlow()

    fun getWorkoutById(id: Int) {
        viewModelScope.launch {
            _currentWorkout.value = repository.getWorkoutById(id)
        }
    }

    fun clearCurrentWorkout() {
        _currentWorkout.value = null
    }

    fun addWorkout(date: Long, exercise: String, sets: Int, reps: Int, weight: Float, duration: Int, photoUri: String?) {
        viewModelScope.launch {
            val workout = WorkoutEntity(
                date = date,
                exercise = exercise,
                sets = sets,
                reps = reps,
                weight = weight,
                duration = duration,
                photoUri = photoUri
            )
            repository.insertWorkout(workout)
        }
    }

    fun updateWorkout(workout: WorkoutEntity) {
        viewModelScope.launch {
            repository.updateWorkout(workout)
        }
    }

    fun deleteWorkout(workout: WorkoutEntity) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }

    // Health Connect & Sensors
    val todaySteps: StateFlow<Long> = stepCounterManager.currentSteps

    private val _todaySleepMinutes = MutableStateFlow(0L)
    val todaySleepMinutes: StateFlow<Long> = _todaySleepMinutes.asStateFlow()
    
    fun startStepCounter() {
        stepCounterManager.startListening()
    }

    override fun onCleared() {
        super.onCleared()
        stepCounterManager.stopListening()
    }

    fun fetchHealthData() {
        viewModelScope.launch {
            if (healthConnectManager.isSupported) {
                // We no longer get steps from HC, only sleep duration
                _todaySleepMinutes.value = healthConnectManager.getTodaySleepDurationMinutes()
            }
        }
    }

    // Blood Pressure
    val allBloodPressures: StateFlow<List<BloodPressureEntity>> = repository.getAllBloodPressures()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isAnalyzingBp = MutableStateFlow(false)
    val isAnalyzingBp: StateFlow<Boolean> = _isAnalyzingBp.asStateFlow()

    fun analyzeBloodPressureFromImage(bitmap: Bitmap, onResult: (BloodPressureEntity?) -> Unit) {
        viewModelScope.launch {
            _isAnalyzingBp.value = true
            val result = geminiRepository.analyzeBloodPressureImage(bitmap)
            _isAnalyzingBp.value = false
            onResult(result)
        }
    }

    fun addBloodPressure(systolic: Int, diastolic: Int, pulse: Int, photoUri: String?) {
        viewModelScope.launch {
            val bp = BloodPressureEntity(
                date = System.currentTimeMillis(),
                systolic = systolic,
                diastolic = diastolic,
                pulse = pulse,
                photoUri = photoUri
            )
            repository.insertBloodPressure(bp)
        }
    }
}
