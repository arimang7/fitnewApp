package com.example.fitnessapp.di

import android.content.Context
import androidx.room.Room
import com.example.fitnessapp.data.AppDatabase
import com.example.fitnessapp.data.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fitness_database"
        ).build()
    }

    @Provides
    fun provideWorkoutDao(database: AppDatabase): WorkoutDao {
        return database.workoutDao()
    }
}
