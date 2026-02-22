# Fitness App 🏃‍♂️💪

A comprehensive Android health and fitness management application that allows users to seamlessly log workouts, track real-time daily steps, monitor sleep duration, and record blood pressure using AI-based image recognition.

## 🌟 Key Features

- **Real-Time Step Tracking (Pedometer)**
  - Integrates directly with the smartphone's hardware sensor (`Sensor.TYPE_STEP_COUNTER`) to accurately measure steps in real-time without delay or data loss.
  - Tracking continues in the Android background even when the app is closed. Daily step counts are securely persisted using `SharedPreferences`.
- **Health Data Integration (Health Connect)**
  - Utilizes Google's latest health platform, `Health Connect API`, to pull and display **sleep data** recorded by Samsung Health, smartwatches, and other health ecosystems.
- **Workout Logging**
  - Allows users to log detailed workout routines, including specific target areas, sets, reps, weight, and duration.
  - Data is managed using a local `Room Database`, ensuring an offline-first experience and lightning-fast performance.
- **AI-Powered Blood Pressure OCR (Gemini API)**
  - Leverages Google's **Gemini Pro Vision API**. Users can simply take a photo of their blood pressure monitor's screen or upload one from the gallery. The AI automatically analyzes the image, extracts the numeric values for Systolic, Diastolic blood pressure, and Pulse, and saves them directly to the database.
- **Statistics & Charts**
  - Incorporates the `MPAndroidChart` library to provide intuitive visual graphs and charts, allowing users to easily track their workout history and observe trends in their physical data over time.

---

## 🛠 Tech Stack

### **Core**

- **Language**: Kotlin
- **Asynchronous / Reactive**: Coroutines, Flow (StateFlow)
- **Dependency Injection (DI)**: Dagger Hilt
- **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern

### **UI & Design**

- **UI Toolkit**: Jetpack Compose
- **Theming & Components**: Material Design 3, Bottom Navigation Bar (Scaffold)
- **Charting**: MPAndroidChart

### **Local & Data Layer**

- **Local DB**: Room Database
- **Sensors & Hardware**: Android `SensorManager` (ACTIVITY_RECOGNITION)
- **Health Platform**: Google Health Connect API

### **AI & Network**

- **Generative AI**: `google-generativeai` SDK (Gemini API)

---

## 📁 Project Structure

The project is built around the **MVVM & Repository Pattern**, separating concerns and ensuring ease of maintenance and scalability.

```text
app/src/main/java/com/example/fitnessapp/
 ├── data/              # Data Definition Layer
 │   ├── WorkoutEntity, BloodPressureEntity  # Room Database Tables (Schemas)
 │   └── WorkoutDao, AppDatabase             # Database Queries and Room Instance
 │
 ├── di/                # Hilt Dependency Injection Setup
 │   └── AppModule      # Provides instances for Repositories, Databases, etc. (Provider)
 │
 ├── repository/        # Mediates between Business Logic and Data Sources (Local/API)
 │   ├── WorkoutRepository       # Handles CRUD operations for workout data in Room DB
 │   ├── HealthConnectManager    # Interfaces with Health Connect to fetch sleep data
 │   ├── StepCounterManager      # Connects to device hardware sensors for live step counting
 │   └── GeminiRepository        # Sends BP monitor images to Gemini AI and parses results
 │
 ├── viewmodel/         # The bridge between the View (UI) and the Repositories
 │   └── WorkoutViewModel        # Holds observable StateFlows for the UI (Lifecycle aware)
 │
 └── ui/                # Jetpack Compose UI Screens
     ├── FitnessAppNavigation    # Manages routing and navigation paths using a Bottom Nav Bar
     ├── HomeScreen              # Main Dashboard (Steps, Sleep, Recent Workouts)
     ├── AddEditScreen           # Form to add or edit workout logs
     ├── HistoryScreen           # List view for browsing historical workouts and blood pressure logs
     ├── StatsScreen             # Statistical charts for visualizing data trends
     └── SettingsScreen          # Permission management and other app settings
```

---

## ⚙️ How to Run & Requirements

1. **Environment**: `Android Studio` (Latest version recommended).
2. **Build Configuration**: Perform a Gradle Sync (Refer to `build.gradle.kts`).
3. **API Key Setup (.env Required)**:
   - A **Google Gemini API Key** is mandatory for the AI image analysis feature to work.
   - Create a `.env` file at the root level of the project and register your key as follows:
     ```properties
     GEMINI_API_KEY=your_actual_api_key_here
     ```
4. **Permissions**:
   - Upon first launch, you must allow the **"Physical Activity" (`ACTIVITY_RECOGNITION`)** permission for the real-time step counter to function.
   - Touching the Sleep/Health dashboard area will prompt for **Health Connect access permissions**. These must be fully granted for sleep duration to be displayed.

---

## 📊 Data Binding Process Flow

**Example 1. Real-Time Step Counting Flow**

1. The Android hardware sensor (`StepCounterManager`) detects user steps.
2. The detected data is immediately emitted to a `StateFlow` and passed to the `WorkoutViewModel`.
3. The `HomeScreen` Compose UI, which is observing the `WorkoutViewModel`, detects the state change and automatically triggers a recomposition, instantly updating the step count number on the dashboard card.

**Example 2. AI Blood Pressure Image Analysis Flow**

1. The user takes a photo of their blood pressure monitor (`HomeScreen` -> `BloodPressure`).
2. The Bitmap information of the photo is passed to the `GeminiRepository`, which transmits it to the cloud along with a predefined AI prompt.
3. The Gemini AI model analyzes the image and responds with a parsed text format (e.g., `120/80/70`).
4. The ViewModel converts this information into a `BloodPressureEntity`, saves it directly into the local database (`Room`), and the data is immediately rendered onto the history charts.
