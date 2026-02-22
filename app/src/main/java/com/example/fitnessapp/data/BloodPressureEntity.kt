package com.example.fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blood_pressure")
data class BloodPressureEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long, // Epoch timestamp in milliseconds
    val systolic: Int, // 수축기 혈압
    val diastolic: Int, // 이완기 혈압
    val pulse: Int, // 맥박
    val photoUri: String? // Optional string representing the URI of an attached photo
)
