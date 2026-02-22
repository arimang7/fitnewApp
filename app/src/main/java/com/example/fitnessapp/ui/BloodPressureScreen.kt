package com.example.fitnessapp.ui

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.fitnessapp.viewmodel.WorkoutViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodPressureScreen(
    onNavigateBack: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var createdTempUri by remember { mutableStateOf<Uri?>(null) }
    
    val isAnalyzing by viewModel.isAnalyzingBp.collectAsState()

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && createdTempUri != null) {
            photoUri = createdTempUri
            
            // Get bitmap from URI
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, createdTempUri!!)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, createdTempUri)
            }
            // Trigger Gemini OCR
            viewModel.analyzeBloodPressureFromImage(bitmap) { result ->
                if (result != null) {
                    systolic = result.systolic.toString()
                    diastolic = result.diastolic.toString()
                    pulse = result.pulse.toString()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("혈압 기록 (Gemini OCR)") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val file = File.createTempFile("BP_${timeStamp}_", ".jpg", storageDir)
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    createdTempUri = uri
                    cameraLauncher.launch(uri)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isAnalyzing
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Camera")
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isAnalyzing) "분석 중 (Gemini)..." else "혈압계 사진 찍기 (자동입력)")
            }

            if (isAnalyzing) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (photoUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = "BP Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            OutlinedTextField(
                value = systolic,
                onValueChange = { systolic = it.filter { char -> char.isDigit() } },
                label = { Text("수축기 (최고) 혈압") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = diastolic,
                onValueChange = { diastolic = it.filter { char -> char.isDigit() } },
                label = { Text("이완기 (최저) 혈압") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = pulse,
                onValueChange = { pulse = it.filter { char -> char.isDigit() } },
                label = { Text("맥박") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val sys = systolic.toIntOrNull() ?: 0
                    val dia = diastolic.toIntOrNull() ?: 0
                    val pls = pulse.toIntOrNull() ?: 0
                    
                    viewModel.addBloodPressure(sys, dia, pls, photoUri?.toString())
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = systolic.isNotBlank() && diastolic.isNotBlank() && !isAnalyzing
            ) {
                Text("저장")
            }
        }
    }
}
