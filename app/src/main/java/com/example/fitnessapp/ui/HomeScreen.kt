package com.example.fitnessapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.core.content.ContextCompat
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnessapp.R
import com.example.fitnessapp.data.WorkoutEntity
import com.example.fitnessapp.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToAddEdit: (Int?) -> Unit,
    onNavigateToBloodPressure: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    val workouts by viewModel.allWorkouts.collectAsState()
    val steps by viewModel.todaySteps.collectAsState()
    val sleepMinutes by viewModel.todaySleepMinutes.collectAsState()

    // Gemini Theme Colors
    val geminiDarkBackground = Color(0xFF0F0F11)
    val geminiCardBackground = Color(0xFF1E1E24)
    val geminiNeonBlue = Color(0xFF4A90E2)
    val geminiNeonPurple = Color(0xFF9013FE)
    val geminiGradient = Brush.horizontalGradient(listOf(geminiNeonBlue, geminiNeonPurple))
    val geminiTextPrimary = Color.White
    val geminiTextSecondary = Color(0xFFA0A0A0)

    val activityPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            android.widget.Toast.makeText(context, "걸음 수 측정 권한이 허용되었습니다.", android.widget.Toast.LENGTH_SHORT).show()
            viewModel.startStepCounter()
        } else {
            android.widget.Toast.makeText(context, "걸음 수 기능을 위해 권한이 필요합니다.", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    val healthPermissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class)
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = PermissionController.createRequestPermissionResultContract()
    ) { granted ->
        if (granted.containsAll(healthPermissions)) {
            viewModel.fetchHealthData()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
                viewModel.startStepCounter()
            } else {
                activityPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        } else {
            viewModel.startStepCounter()
        }

        if (HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE) {
            val hcClient = HealthConnectClient.getOrCreate(context)
            val granted = hcClient.permissionController.getGrantedPermissions()
            if (granted.containsAll(healthPermissions)) {
                viewModel.fetchHealthData()
            } else {
                permissionLauncher.launch(healthPermissions)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = geminiDarkBackground,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "운동 목록", 
                        color = geminiTextPrimary, 
                        fontWeight = FontWeight.Bold
                    ) 
                },
                actions = {
                    IconButton(onClick = onNavigateToBloodPressure) {
                        Icon(Icons.Filled.Favorite, contentDescription = "혈압 기록", tint = geminiNeonPurple)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = geminiDarkBackground,
                    titleContentColor = geminiTextPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddEdit(null) },
                containerColor = Color.Transparent,
                modifier = Modifier.background(geminiGradient, RoundedCornerShape(16.dp))
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_workout), tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Health Connect Dashboard
            val onSleepClick: () -> Unit = {
                coroutineScope.launch {
                    try {
                        if (HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE) {
                            val hcClient = HealthConnectClient.getOrCreate(context)
                            val granted = hcClient.permissionController.getGrantedPermissions()
                            if (granted.containsAll(healthPermissions)) {
                                android.widget.Toast.makeText(context, "수면 데이터 새로고침 중...", android.widget.Toast.LENGTH_SHORT).show()
                                viewModel.fetchHealthData()
                            } else {
                                android.widget.Toast.makeText(context, "수면 데이터 권한이 없습니다.", android.widget.Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, PermissionsRationaleActivity::class.java)
                                context.startActivity(intent)
                            }
                        } else {
                            android.widget.Toast.makeText(context, "Health Connect 지원 안 됨.", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GeminiDashboardCard(
                    title = "오늘의 걸음",
                    value = "$steps 걸음",
                    modifier = Modifier.weight(1f),
                    gradient = geminiGradient,
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                                activityPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                            } else {
                                android.widget.Toast.makeText(context, "실시간 걸음수를 측정 중입니다: $steps", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            android.widget.Toast.makeText(context, "실시간 걸음수를 측정 중입니다: $steps", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                
                GeminiDashboardCard(
                    title = "수면 시간",
                    value = "${sleepMinutes / 60}시간 ${sleepMinutes % 60}분",
                    modifier = Modifier.weight(1f),
                    gradient = geminiGradient,
                    onClick = onSleepClick
                )
            }

            Text("최근 운동", color = geminiTextPrimary, style = MaterialTheme.typography.titleMedium)

            if (workouts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_workouts),
                        style = MaterialTheme.typography.bodyLarge,
                        color = geminiTextSecondary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(workouts) { workout ->
                        GeminiWorkoutItem(
                            workout = workout,
                            onClick = { onNavigateToAddEdit(workout.id) },
                            cardColor = geminiCardBackground,
                            textColor = geminiTextPrimary,
                            subTextColor = geminiTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiDashboardCard(title: String, value: String, modifier: Modifier = Modifier, gradient: Brush, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.border(1.dp, gradient, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E24)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color(0xFFA0A0A0), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun GeminiWorkoutItem(workout: WorkoutEntity, onClick: () -> Unit, cardColor: Color, textColor: Color, subTextColor: Color) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateString = dateFormat.format(Date(workout.date))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = workout.exercise,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dateString,
                    color = subTextColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${workout.sets} Sets x ${workout.reps} Reps @ ${workout.weight}kg",
                    color = subTextColor,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${workout.duration} min",
                    color = subTextColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
