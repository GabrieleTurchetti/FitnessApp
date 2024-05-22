package com.example.fitnessapp.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "steps")
data class Steps(
    @PrimaryKey @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "steps") val steps: Int,
    @ColumnInfo(name = "steps_at_last_reboot") val stepsAtLastReboot: Int,
    @ColumnInfo(name = "initial_steps") val initialSteps: Int
)

@Entity(tableName = "calories")
data class Calories(
    @PrimaryKey @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "calories") val calories: Int,
)

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "longitude") val longitude: Float
)

@Dao
interface FitnessDao {
    @Query("SELECT * FROM steps WHERE date == :date")
    suspend fun getStepsByDate(date: String): List<Steps>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(vararg steps: Steps)

    @Query("SELECT * FROM calories WHERE date == :date")
    suspend fun getCaloriesByDate(date: String): List<Calories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalories(vararg calories: Calories)

    @Query("SELECT * FROM locations WHERE date == :date")
    suspend fun getLocationsByDate(date: String): List<Location>

    @Insert
    suspend fun insertLocation(vararg location: Location)
}

@Database(entities = [Steps::class, Calories::class, Location::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fitnessDao(): FitnessDao
}