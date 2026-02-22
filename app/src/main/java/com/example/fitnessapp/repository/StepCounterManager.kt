package com.example.fitnessapp.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepCounterManager @Inject constructor(
    @ApplicationContext private val context: Context
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private val _currentSteps = MutableStateFlow(0L)
    val currentSteps: StateFlow<Long> = _currentSteps.asStateFlow()

    private var isListening = false
    private val prefs = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)

    fun startListening() {
        if (stepSensor != null && !isListening) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            isListening = true
        }
    }

    fun stopListening() {
        if (isListening) {
            sensorManager.unregisterListener(this)
            isListening = false
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val totalStepsSinceReboot = it.values[0].toLong()
                
                val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val savedBaseSteps = prefs.getLong("base_steps_$todayStr", -1L)
                
                if (savedBaseSteps == -1L) {
                    // Start of day or first install. Save baseline. 
                    // Note: If you want to show raw values now to test, you could use totalStepsSinceReboot, 
                    // but difference gives exact steps per day moving forward.
                    prefs.edit().putLong("base_steps_$todayStr", totalStepsSinceReboot).apply()
                    _currentSteps.value = 0L // Initialize to 0 for the first reading
                } else {
                    var stepsTaken = totalStepsSinceReboot - savedBaseSteps
                    if (stepsTaken < 0) {
                        // Device rebooted. Reset baseline.
                        prefs.edit().putLong("base_steps_$todayStr", 0L).apply()
                        stepsTaken = totalStepsSinceReboot
                    }
                    _currentSteps.value = stepsTaken
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for step counter
    }
}
