package com.example.fitnessapp.viewmodel;

import com.example.fitnessapp.repository.GeminiRepository;
import com.example.fitnessapp.repository.HealthConnectManager;
import com.example.fitnessapp.repository.StepCounterManager;
import com.example.fitnessapp.repository.WorkoutRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class WorkoutViewModel_Factory implements Factory<WorkoutViewModel> {
  private final Provider<WorkoutRepository> repositoryProvider;

  private final Provider<HealthConnectManager> healthConnectManagerProvider;

  private final Provider<StepCounterManager> stepCounterManagerProvider;

  private final Provider<GeminiRepository> geminiRepositoryProvider;

  public WorkoutViewModel_Factory(Provider<WorkoutRepository> repositoryProvider,
      Provider<HealthConnectManager> healthConnectManagerProvider,
      Provider<StepCounterManager> stepCounterManagerProvider,
      Provider<GeminiRepository> geminiRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.healthConnectManagerProvider = healthConnectManagerProvider;
    this.stepCounterManagerProvider = stepCounterManagerProvider;
    this.geminiRepositoryProvider = geminiRepositoryProvider;
  }

  @Override
  public WorkoutViewModel get() {
    return newInstance(repositoryProvider.get(), healthConnectManagerProvider.get(), stepCounterManagerProvider.get(), geminiRepositoryProvider.get());
  }

  public static WorkoutViewModel_Factory create(Provider<WorkoutRepository> repositoryProvider,
      Provider<HealthConnectManager> healthConnectManagerProvider,
      Provider<StepCounterManager> stepCounterManagerProvider,
      Provider<GeminiRepository> geminiRepositoryProvider) {
    return new WorkoutViewModel_Factory(repositoryProvider, healthConnectManagerProvider, stepCounterManagerProvider, geminiRepositoryProvider);
  }

  public static WorkoutViewModel newInstance(WorkoutRepository repository,
      HealthConnectManager healthConnectManager, StepCounterManager stepCounterManager,
      GeminiRepository geminiRepository) {
    return new WorkoutViewModel(repository, healthConnectManager, stepCounterManager, geminiRepository);
  }
}
