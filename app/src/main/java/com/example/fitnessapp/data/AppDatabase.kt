package com.example.fitnessapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WorkoutEntity::class, BloodPressureEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}
