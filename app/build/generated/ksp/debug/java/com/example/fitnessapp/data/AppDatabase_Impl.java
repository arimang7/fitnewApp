package com.example.fitnessapp.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile WorkoutDao _workoutDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `workouts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `exercise` TEXT NOT NULL, `sets` INTEGER NOT NULL, `reps` INTEGER NOT NULL, `weight` REAL NOT NULL, `duration` INTEGER NOT NULL, `photoUri` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `blood_pressure` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `systolic` INTEGER NOT NULL, `diastolic` INTEGER NOT NULL, `pulse` INTEGER NOT NULL, `photoUri` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd6702a57b77cc813916dacf2d37d0b3a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `workouts`");
        db.execSQL("DROP TABLE IF EXISTS `blood_pressure`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWorkouts = new HashMap<String, TableInfo.Column>(8);
        _columnsWorkouts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("exercise", new TableInfo.Column("exercise", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("sets", new TableInfo.Column("sets", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("reps", new TableInfo.Column("reps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("weight", new TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkouts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkouts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkouts = new TableInfo("workouts", _columnsWorkouts, _foreignKeysWorkouts, _indicesWorkouts);
        final TableInfo _existingWorkouts = TableInfo.read(db, "workouts");
        if (!_infoWorkouts.equals(_existingWorkouts)) {
          return new RoomOpenHelper.ValidationResult(false, "workouts(com.example.fitnessapp.data.WorkoutEntity).\n"
                  + " Expected:\n" + _infoWorkouts + "\n"
                  + " Found:\n" + _existingWorkouts);
        }
        final HashMap<String, TableInfo.Column> _columnsBloodPressure = new HashMap<String, TableInfo.Column>(6);
        _columnsBloodPressure.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBloodPressure.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBloodPressure.put("systolic", new TableInfo.Column("systolic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBloodPressure.put("diastolic", new TableInfo.Column("diastolic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBloodPressure.put("pulse", new TableInfo.Column("pulse", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBloodPressure.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBloodPressure = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBloodPressure = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBloodPressure = new TableInfo("blood_pressure", _columnsBloodPressure, _foreignKeysBloodPressure, _indicesBloodPressure);
        final TableInfo _existingBloodPressure = TableInfo.read(db, "blood_pressure");
        if (!_infoBloodPressure.equals(_existingBloodPressure)) {
          return new RoomOpenHelper.ValidationResult(false, "blood_pressure(com.example.fitnessapp.data.BloodPressureEntity).\n"
                  + " Expected:\n" + _infoBloodPressure + "\n"
                  + " Found:\n" + _existingBloodPressure);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d6702a57b77cc813916dacf2d37d0b3a", "f12e4a52ebf4d0c7f9b325a2fb8b15ef");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "workouts","blood_pressure");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `workouts`");
      _db.execSQL("DELETE FROM `blood_pressure`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WorkoutDao.class, WorkoutDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WorkoutDao workoutDao() {
    if (_workoutDao != null) {
      return _workoutDao;
    } else {
      synchronized(this) {
        if(_workoutDao == null) {
          _workoutDao = new WorkoutDao_Impl(this);
        }
        return _workoutDao;
      }
    }
  }
}
