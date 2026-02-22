package com.example.fitnessapp.repository

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthConnectManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val isSupported: Boolean = HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE
    
    // We instantiate client lazily in case it's not supported
    val client: HealthConnectClient? by lazy {
        if (isSupported) HealthConnectClient.getOrCreate(context) else null
    }

    suspend fun getTodaySteps(): Long {
        if (client == null) return 0L
        
        val now = Instant.now()
        val startOfDay = now.atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()

        return try {
            val response = client!!.aggregate(
                androidx.health.connect.client.request.AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(startOfDay, now)
                )
            )
            response[StepsRecord.COUNT_TOTAL] ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    suspend fun getTodaySleepDurationMinutes(): Long {
        if (client == null) return 0L

        val now = Instant.now()
        // Check sleep from yesterday evening to now
        val yesterdayEvening = now.minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).withHour(18).withMinute(0).toInstant()

        return try {
            val response = client!!.aggregate(
                androidx.health.connect.client.request.AggregateRequest(
                    metrics = setOf(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(yesterdayEvening, now)
                )
            )
            response[SleepSessionRecord.SLEEP_DURATION_TOTAL]?.toMinutes() ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }
}
