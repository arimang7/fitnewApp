package com.example.fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long, // Epoch timestamp in milliseconds
    val exercise: String,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    val duration: Int, // Duration in minutes
    val photoUri: String? // Optional string representing the URI of an attached photo
)
