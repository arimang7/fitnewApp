package com.example.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnessapp.R
import com.example.fitnessapp.viewmodel.WorkoutViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    onNavigateBack: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val workouts by viewModel.allWorkouts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.stats_title)) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(stringResource(id = R.string.weekly_volume), style = MaterialTheme.typography.titleLarge)
            
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                factory = { context ->
                    LineChart(context).apply {
                        description.isEnabled = false
                        setDrawGridBackground(false)
                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                        axisRight.isEnabled = false
                    }
                },
                update = { chart ->
                    // Group by date simply mapping index to days ago
                    val sortedWorkouts = workouts.sortedBy { it.date }
                    val entries = sortedWorkouts.mapIndexed { index, workout ->
                        val volume = workout.sets * workout.reps * workout.weight
                        Entry(index.toFloat(), volume)
                    }

                    if (entries.isNotEmpty()) {
                        val dataSet = LineDataSet(entries, "Volume")
                        dataSet.color = android.graphics.Color.parseColor("#006782")
                        dataSet.setDrawCircles(true)
                        dataSet.setDrawValues(false)
                        dataSet.lineWidth = 2f
                        
                        chart.data = LineData(dataSet)
                        chart.invalidate()
                    } else {
                        chart.clear()
                    }
                }
            )

            Text("개인 최고 기록 (PB)", style = MaterialTheme.typography.titleLarge)
            
            // PB calc
            val maxWeightWorkout = workouts.maxByOrNull { it.weight }
            if (maxWeightWorkout != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Top Weight: ${maxWeightWorkout.weight}kg",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Exercise: ${maxWeightWorkout.exercise}")
                    }
                }
            } else {
                Text("기록이 없습니다.")
            }
        }
    }
}
