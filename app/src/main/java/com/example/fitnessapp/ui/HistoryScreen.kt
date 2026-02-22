package com.example.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnessapp.R
import com.example.fitnessapp.viewmodel.WorkoutViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val workouts by viewModel.allWorkouts.collectAsState()
    var selectedDateMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.history_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Button(onClick = { showDatePicker = true }, modifier = Modifier.fillMaxWidth()) {
                Text("날짜 선택 (Select Date)")
            }

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { selectedDateMillis = it }
                            showDatePicker = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calculate start and end of selected day
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDateMillis
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            val startOfDay = calendar.timeInMillis
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            val endOfDay = calendar.timeInMillis

            val dailyWorkouts = workouts.filter { it.date in startOfDay..endOfDay }

            if (dailyWorkouts.isEmpty()) {
                Text("선택한 날짜에 운동 기록이 없습니다.", modifier = Modifier.padding(16.dp))
            } else {
                Text(
                    "오늘의 운동량 (총 ${dailyWorkouts.size}개)",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    factory = { context ->
                        BarChart(context).apply {
                            description.isEnabled = false
                            setDrawGridBackground(false)
                            xAxis.position = XAxis.XAxisPosition.BOTTOM
                            xAxis.setDrawGridLines(false)
                            axisRight.isEnabled = false
                        }
                    },
                    update = { chart ->
                        val entries = dailyWorkouts.mapIndexed { index, workout ->
                            // Calculate volume
                            val volume = workout.sets * workout.reps * workout.weight
                            BarEntry(index.toFloat(), volume)
                        }
                        
                        if (entries.isNotEmpty()) {
                            val dataSet = BarDataSet(entries, "운동 볼륨 (Volume = Sets x Reps x Weight)")
                            dataSet.color = android.graphics.Color.parseColor("#006782")
                            val barData = BarData(dataSet)
                            barData.barWidth = 0.5f
                            chart.data = barData
                            chart.invalidate()
                        } else {
                            chart.clear()
                        }
                    }
                )
            }
        }
    }
}
