package com.example.fitnessapp.repository

import com.example.fitnessapp.data.BloodPressureEntity
import com.example.fitnessapp.data.WorkoutDao
import com.example.fitnessapp.data.WorkoutEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao
) {
    fun getAllWorkouts(): Flow<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    suspend fun getWorkoutById(id: Int): WorkoutEntity? = workoutDao.getWorkoutById(id)
    
    fun getWorkoutsBetweenDates(startDate: Long, endDate: Long): Flow<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsBetweenDates(startDate, endDate)
    }

    suspend fun insertWorkout(workout: WorkoutEntity) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun updateWorkout(workout: WorkoutEntity) {
        workoutDao.updateWorkout(workout)
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }

    // Blood Pressure
    fun getAllBloodPressures(): Flow<List<BloodPressureEntity>> = workoutDao.getAllBloodPressures()

    suspend fun insertBloodPressure(bp: BloodPressureEntity) {
        workoutDao.insertBloodPressure(bp)
    }
}
