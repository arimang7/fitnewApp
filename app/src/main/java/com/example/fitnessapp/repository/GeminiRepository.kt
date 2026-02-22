package com.example.fitnessapp.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.fitnessapp.BuildConfig
import com.example.fitnessapp.data.BloodPressureEntity
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // API KEY is parsed by secrets-gradle-plugin from .env and injected into BuildConfig
    private val apiKey = BuildConfig.GEMINI_API_KEY
    private val modelName = BuildConfig.GEMINI_MODEL

    private val generativeModel = GenerativeModel(
        // Fallback to gemini-1.5-flash if BUILD_CONFIG model name is empty or missing
        modelName = if (modelName.isNotEmpty()) modelName else "gemini-1.5-flash",
        apiKey = apiKey
    )

    suspend fun analyzeBloodPressureImage(bitmap: Bitmap): BloodPressureEntity? = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    image(bitmap)
                    text("Analyze this blood pressure monitor screen. Provide the result strictly in valid JSON format ONLY, without any markdown formatting. {\"systolic\": 120, \"diastolic\": 80, \"pulse\": 70}. If you cannot find the numbers, return {\"error\": \"not found\"}.")
                }
            )

            val textResult = response.text ?: return@withContext null
            // Remove markdown code blocks if the model wrapped it
            val cleanedJson = textResult.replace("```json", "").replace("```", "").trim()
            val jsonObject = JSONObject(cleanedJson)

            if (jsonObject.has("error")) {
                return@withContext null
            }

            BloodPressureEntity(
                date = System.currentTimeMillis(),
                systolic = jsonObject.getInt("systolic"),
                diastolic = jsonObject.getInt("diastolic"),
                pulse = jsonObject.getInt("pulse"),
                photoUri = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
