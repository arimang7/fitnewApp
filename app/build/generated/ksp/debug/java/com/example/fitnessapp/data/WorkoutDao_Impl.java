package com.example.fitnessapp.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkoutDao_Impl implements WorkoutDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutEntity> __insertionAdapterOfWorkoutEntity;

  private final EntityInsertionAdapter<BloodPressureEntity> __insertionAdapterOfBloodPressureEntity;

  private final EntityDeletionOrUpdateAdapter<WorkoutEntity> __deletionAdapterOfWorkoutEntity;

  private final EntityDeletionOrUpdateAdapter<WorkoutEntity> __updateAdapterOfWorkoutEntity;

  public WorkoutDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutEntity = new EntityInsertionAdapter<WorkoutEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workouts` (`id`,`date`,`exercise`,`sets`,`reps`,`weight`,`duration`,`photoUri`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getExercise());
        statement.bindLong(4, entity.getSets());
        statement.bindLong(5, entity.getReps());
        statement.bindDouble(6, entity.getWeight());
        statement.bindLong(7, entity.getDuration());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPhotoUri());
        }
      }
    };
    this.__insertionAdapterOfBloodPressureEntity = new EntityInsertionAdapter<BloodPressureEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `blood_pressure` (`id`,`date`,`systolic`,`diastolic`,`pulse`,`photoUri`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BloodPressureEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindLong(3, entity.getSystolic());
        statement.bindLong(4, entity.getDiastolic());
        statement.bindLong(5, entity.getPulse());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhotoUri());
        }
      }
    };
    this.__deletionAdapterOfWorkoutEntity = new EntityDeletionOrUpdateAdapter<WorkoutEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workouts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWorkoutEntity = new EntityDeletionOrUpdateAdapter<WorkoutEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workouts` SET `id` = ?,`date` = ?,`exercise` = ?,`sets` = ?,`reps` = ?,`weight` = ?,`duration` = ?,`photoUri` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindString(3, entity.getExercise());
        statement.bindLong(4, entity.getSets());
        statement.bindLong(5, entity.getReps());
        statement.bindDouble(6, entity.getWeight());
        statement.bindLong(7, entity.getDuration());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPhotoUri());
        }
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertWorkout(final WorkoutEntity workout,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWorkoutEntity.insert(workout);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertBloodPressure(final BloodPressureEntity bp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBloodPressureEntity.insert(bp);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkout(final WorkoutEntity workout,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutEntity.handle(workout);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorkout(final WorkoutEntity workout,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutEntity.handle(workout);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WorkoutEntity>> getAllWorkouts() {
    final String _sql = "SELECT * FROM workouts ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workouts"}, new Callable<List<WorkoutEntity>>() {
      @Override
      @NonNull
      public List<WorkoutEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfExercise = CursorUtil.getColumnIndexOrThrow(_cursor, "exercise");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final List<WorkoutEntity> _result = new ArrayList<WorkoutEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpExercise;
            _tmpExercise = _cursor.getString(_cursorIndexOfExercise);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _item = new WorkoutEntity(_tmpId,_tmpDate,_tmpExercise,_tmpSets,_tmpReps,_tmpWeight,_tmpDuration,_tmpPhotoUri);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWorkoutById(final int id,
      final Continuation<? super WorkoutEntity> $completion) {
    final String _sql = "SELECT * FROM workouts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorkoutEntity>() {
      @Override
      @Nullable
      public WorkoutEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfExercise = CursorUtil.getColumnIndexOrThrow(_cursor, "exercise");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final WorkoutEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpExercise;
            _tmpExercise = _cursor.getString(_cursorIndexOfExercise);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _result = new WorkoutEntity(_tmpId,_tmpDate,_tmpExercise,_tmpSets,_tmpReps,_tmpWeight,_tmpDuration,_tmpPhotoUri);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WorkoutEntity>> getWorkoutsBetweenDates(final long startDate,
      final long endDate) {
    final String _sql = "SELECT * FROM workouts WHERE date >= ? AND date <= ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workouts"}, new Callable<List<WorkoutEntity>>() {
      @Override
      @NonNull
      public List<WorkoutEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfExercise = CursorUtil.getColumnIndexOrThrow(_cursor, "exercise");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final List<WorkoutEntity> _result = new ArrayList<WorkoutEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpExercise;
            _tmpExercise = _cursor.getString(_cursorIndexOfExercise);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _item = new WorkoutEntity(_tmpId,_tmpDate,_tmpExercise,_tmpSets,_tmpReps,_tmpWeight,_tmpDuration,_tmpPhotoUri);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BloodPressureEntity>> getAllBloodPressures() {
    final String _sql = "SELECT * FROM blood_pressure ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"blood_pressure"}, new Callable<List<BloodPressureEntity>>() {
      @Override
      @NonNull
      public List<BloodPressureEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSystolic = CursorUtil.getColumnIndexOrThrow(_cursor, "systolic");
          final int _cursorIndexOfDiastolic = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolic");
          final int _cursorIndexOfPulse = CursorUtil.getColumnIndexOrThrow(_cursor, "pulse");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final List<BloodPressureEntity> _result = new ArrayList<BloodPressureEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BloodPressureEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpSystolic;
            _tmpSystolic = _cursor.getInt(_cursorIndexOfSystolic);
            final int _tmpDiastolic;
            _tmpDiastolic = _cursor.getInt(_cursorIndexOfDiastolic);
            final int _tmpPulse;
            _tmpPulse = _cursor.getInt(_cursorIndexOfPulse);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _item = new BloodPressureEntity(_tmpId,_tmpDate,_tmpSystolic,_tmpDiastolic,_tmpPulse,_tmpPhotoUri);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
