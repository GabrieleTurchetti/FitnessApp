package com.example.fitnessapp.exercises

import android.util.Log
import androidx.annotation.DrawableRes
import com.example.fitnessapp.CardItem
import com.example.fitnessapp.R

sealed class MuscleGroup(
    override val id: String,
    override val name: String,
    @DrawableRes override val imageId: Int,
    val exercises: List<Exercise>,
    open val onMuscleGroupClick: (muscleGroup: CardItem) -> Unit
) : CardItem(
    id,
    name,
    imageId,
    onMuscleGroupClick
) {
    data class Chest(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "chest",
        "Pettorali",
        R.drawable.ic_chest,
        listOf(
            Exercise("barbellBenchPress", "Barbell Bench Press", R.drawable.barbell_bench_press, {}),
            Exercise("barbellBenchPress", "Diamond Push Up", R.drawable.diamond_push_up, {}),
            Exercise("barbellBenchPress", "Dumbbell Fly", R.drawable.dumbbell_fly, {}),
            Exercise("barbellBenchPress", "High Cable Crossover", R.drawable.high_cable_crossover, {}),
            Exercise("barbellBenchPress", "Incline Dumbbell Press", R.drawable.incline_dumbbell_press, {}),
            Exercise("barbellBenchPress", "Pec Deck Fly", R.drawable.pec_deck_fly, {}),
            Exercise("barbellBenchPress", "Push Up", R.drawable.push_up, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Shoulders(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "shoulders",
        "Spalle",
        R.drawable.ic_shoulders,
        listOf(
            Exercise("barbellBenchPress", "Alternating Dumbbell Front Raise", R.drawable.alternating_dumbbell_front_raise, {}),
            Exercise("barbellBenchPress", "Dumbbell Lateral Raise", R.drawable.dumbbell_lateral_raise, {}),
            Exercise("barbellBenchPress", "Dumbbell Reverse Fly", R.drawable.dumbbell_reverse_fly, {}),
            Exercise("barbellBenchPress", "Dumbbell Shoulder Press", R.drawable.dumbbell_shoulder_press, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Biceps(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "biceps",
        "Bicipiti",
        R.drawable.ic_biceps,
        listOf(
            Exercise("barbellBenchPress", "Concentration Curl", R.drawable.concentration_curl, {}),
            Exercise("barbellBenchPress", "Dumbbell Curl", R.drawable.dumbbell_curl, {}),
            Exercise("barbellBenchPress", "Hammer Curl", R.drawable.hammer_curl, {}),
            Exercise("barbellBenchPress", "Rope Bicep Curls", R.drawable.rope_bicep_curls, {}),
            Exercise("barbellBenchPress", "Z Bar Preacher Curl", R.drawable.z_bar_preacher_curl, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Triceps(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "triceps",
        "Tricipiti",
        R.drawable.ic_triceps,
        listOf(
            Exercise("barbellBenchPress", "Barbell Triceps Extension", R.drawable.barbell_triceps_extension, {}),
            Exercise("barbellBenchPress", "Dumbbell Kickback", R.drawable.dumbbell_kickback, {}),
            Exercise("barbellBenchPress", "Push Down", R.drawable.push_down, {}),
            Exercise("barbellBenchPress", "Seated Dumbbell Triceps Extension", R.drawable.seated_dumbbell_triceps_extension, {}),
            Exercise("barbellBenchPress", "Triceps Dips", R.drawable.triceps_dips, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Abs(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "abs",
        "Addominali",
        R.drawable.ic_abs,
        listOf(
            Exercise("barbellBenchPress", "Cross Crunch", R.drawable.cross_crunch, {}),
            Exercise("barbellBenchPress", "Crunch", R.drawable.crunch, {}),
            Exercise("barbellBenchPress", "Plank", R.drawable.plank, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Back(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "back",
        "Schiena",
        R.drawable.ic_back,
        listOf(
            Exercise("barbellBenchPress", "Dumbbell Row", R.drawable.dumbbell_row, {}),
            Exercise("barbellBenchPress", "Lat Pulldown", R.drawable.lat_pulldown, {}),
            Exercise("barbellBenchPress", "Pull Up", R.drawable.pull_up, {}),
            Exercise("barbellBenchPress", "T Bar Row", R.drawable.t_bar_row, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Legs(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "legs",
        "Gambe",
        R.drawable.ic_legs,
        listOf(
            Exercise("barbellBenchPress", "Barbell Deadlift", R.drawable.barbell_deadlift, {}),
            Exercise("barbellBenchPress", "Barbell Romanian Deadlift", R.drawable.barbell_romanian_deadlift, {}),
            Exercise("barbellBenchPress", "Barbell Squat", R.drawable.barbell_squat, {}),
            Exercise("barbellBenchPress", "Dumbbell Bulgarian Split Squat", R.drawable.dumbbell_bulgarian_split_squat, {}),
            Exercise("barbellBenchPress", "Dumbbell Lunge", R.drawable.dumbbell_lunge, {}),
            Exercise("barbellBenchPress", "Leg Curl", R.drawable.leg_curl, {}),
            Exercise("barbellBenchPress", "Leg Extention", R.drawable.leg_extention, {}),
            Exercise("barbellBenchPress", "Leg Press", R.drawable.leg_press, {}),
            Exercise("barbellBenchPress", "Seated Leg Curl", R.drawable.seated_leg_curl, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Calves(
        override var onMuscleGroupClick: (muscleGroup: CardItem) -> Unit = {}
    ) : MuscleGroup(
        "calves",
        "Polpacci",
        R.drawable.ic_calves,
        listOf(
            Exercise("barbellBenchPress", "Dumbbell Calf Raise", R.drawable.dumbbell_calf_raise, {}),
            Exercise("barbellBenchPress", "Lever Seated Calf Raise", R.drawable.lever_seated_calf_raise, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )

    companion object {
        fun getClassFromId(id: String?) : List<Exercise>{

            return when (id){
                "chest" -> Chest().exercises
                "shoulders" -> Shoulders().exercises
                "biceps" -> Biceps().exercises
                "triceps" -> Triceps().exercises
                "abs" -> Abs().exercises
                "back" -> Back().exercises
                "legs" -> Legs().exercises
                "calves" -> Calves().exercises
                else -> emptyList()
            }
        }
    }
}