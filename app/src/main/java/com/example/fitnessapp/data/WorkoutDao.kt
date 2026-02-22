package com.example.fitnessapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getWorkoutById(id: Int): WorkoutEntity?
    
    @Query("SELECT * FROM workouts WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC")
    fun getWorkoutsBetweenDates(startDate: Long, endDate: Long): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

    // Blood Pressure
    @Query("SELECT * FROM blood_pressure ORDER BY date DESC")
    fun getAllBloodPressures(): Flow<List<BloodPressureEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBloodPressure(bp: BloodPressureEntity)
}
