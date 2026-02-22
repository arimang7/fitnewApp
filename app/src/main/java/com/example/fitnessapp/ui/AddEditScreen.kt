package com.example.fitnessapp.ui

import android.net.Uri
import android.os.Environment
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.fitnessapp.R
import com.example.fitnessapp.viewmodel.WorkoutViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    workoutId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentWorkout by viewModel.currentWorkout.collectAsState()

    var exercise by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var capturedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var createdTempUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            photoUri = createdTempUri
        }
    }

    LaunchedEffect(workoutId) {
        if (workoutId != null) {
            viewModel.getWorkoutById(workoutId)
        } else {
            viewModel.clearCurrentWorkout()
        }
    }

    LaunchedEffect(currentWorkout) {
        currentWorkout?.let { workout ->
            exercise = workout.exercise
            sets = workout.sets.toString()
            reps = workout.reps.toString()
            weight = workout.weight.toString()
            duration = workout.duration.toString()
            photoUri = workout.photoUri?.let { Uri.parse(it) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = if (workoutId == null) R.string.add_workout else R.string.edit_workout)) },
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
            OutlinedTextField(
                value = exercise,
                onValueChange = { exercise = it },
                label = { Text(stringResource(id = R.string.exercise_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = sets,
                    onValueChange = { sets = it.filter { char -> char.isDigit() } },
                    label = { Text(stringResource(id = R.string.sets)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it.filter { char -> char.isDigit() } },
                    label = { Text(stringResource(id = R.string.reps)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(stringResource(id = R.string.weight)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it.filter { char -> char.isDigit() } },
                    label = { Text(stringResource(id = R.string.duration)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Button(
                onClick = {
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    createdTempUri = uri
                    cameraLauncher.launch(uri)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Camera")
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(id = R.string.take_photo))
            }

            if (photoUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = "Workout Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val currentSets = sets.toIntOrNull() ?: 0
                    val currentReps = reps.toIntOrNull() ?: 0
                    val currentWeight = weight.toFloatOrNull() ?: 0f
                    val currentDuration = duration.toIntOrNull() ?: 0
                    
                    if (workoutId == null) {
                        viewModel.addWorkout(
                            date = System.currentTimeMillis(),
                            exercise = exercise,
                            sets = currentSets,
                            reps = currentReps,
                            weight = currentWeight,
                            duration = currentDuration,
                            photoUri = photoUri?.toString()
                        )
                    } else {
                        currentWorkout?.let { workout ->
                            viewModel.updateWorkout(
                                workout.copy(
                                    exercise = exercise,
                                    sets = currentSets,
                                    reps = currentReps,
                                    weight = currentWeight,
                                    duration = currentDuration,
                                    photoUri = photoUri?.toString()
                                )
                            )
                        }
                    }
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = exercise.isNotBlank()
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}
