package com.example.fitnessapp.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "steps")
data class StepCount(
    @PrimaryKey @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "steps") val steps: Int,
    @ColumnInfo(name = "steps_at_last_reboot") val stepsAtLastReboot: Int,
    @ColumnInfo(name = "initial_steps") val initialSteps: Int
)

@Dao
interface StepsDao {
    @Query("SELECT * FROM steps")
    suspend fun getAll(): List<StepCount>

    @Query("SELECT * FROM steps WHERE date == :date")
    suspend fun getByDate(date: String): Array<StepCount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg steps: StepCount)

    @Delete
    suspend fun delete(steps: StepCount)
}

@Database(entities = [StepCount::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stepsDao(): StepsDao
}