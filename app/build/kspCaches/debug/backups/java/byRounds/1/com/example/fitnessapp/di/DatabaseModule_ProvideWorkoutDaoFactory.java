package com.example.fitnessapp.di;

import com.example.fitnessapp.data.AppDatabase;
import com.example.fitnessapp.data.WorkoutDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideWorkoutDaoFactory implements Factory<WorkoutDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideWorkoutDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WorkoutDao get() {
    return provideWorkoutDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWorkoutDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWorkoutDaoFactory(databaseProvider);
  }

  public static WorkoutDao provideWorkoutDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWorkoutDao(database));
  }
}
