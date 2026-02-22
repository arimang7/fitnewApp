package com.example.fitnessapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient

class PermissionsRationaleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "권한 필요 안내",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "이 앱은 홈 화면 대시보드에서 삼성 헬스 등의 걸음 수 및 수면 시간을 보여주기 위해 Health Connect 권한이 필요합니다.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(onClick = {
                            val intent = Intent(HealthConnectClient.ACTION_HEALTH_CONNECT_SETTINGS)
                            startActivity(intent)
                            finish()
                        }) {
                            Text("삼성 헬스(Health Connect) 설정 열기")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { finish() }) {
                            Text("닫기")
                        }
                    }
                }
            }
        }
    }
}
