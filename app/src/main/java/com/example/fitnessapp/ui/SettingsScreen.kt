package com.example.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitnessapp.R
import com.example.fitnessapp.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val weeklyGoal by viewModel.weeklyGoal.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings_title)) },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("목표 설정", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("주간 운동 목표 (일)")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (weeklyGoal > 1) viewModel.updateWeeklyGoal(weeklyGoal - 1) }) {
                        Text("-", style = MaterialTheme.typography.titleLarge)
                    }
                    Text(weeklyGoal.toString(), style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = { if (weeklyGoal < 7) viewModel.updateWeeklyGoal(weeklyGoal + 1) }) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
            
            Divider()
            
            Text("알림", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("운동 알림 받기")
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { viewModel.updateNotificationsEnabled(it) }
                )
            }

            Divider()

            Text("데이터 백업", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Button(
                onClick = { /* Backup Logic Here usually invoking system backup provider */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Google Drive 백업 (예제)")
            }
        }
    }
}
