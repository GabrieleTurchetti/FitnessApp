package com.example.fitnessapp.exercises.musclegroup

import androidx.annotation.DrawableRes
import com.example.fitnessapp.ui.common.CardItem
import com.example.fitnessapp.R
import com.example.fitnessapp.exercises.musclegroup.exercise.Exercise

sealed class MuscleGroup(
    override val id: String,
    override val name: String,
    @DrawableRes override val imageId: Int,
    val exercises: List<Exercise>,
    open val onMuscleGroupClick: () -> Unit
) : CardItem(
    id,
    name,
    imageId,
    onMuscleGroupClick
) {
    data class Chest(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "chest",
        "Pettorali",
        R.drawable.ic_chest,
        listOf(
            Exercise("barbellBenchPress", "Barbell Bench Press", R.drawable.barbell_bench_press, {}),
            Exercise("diamondPushUp", "Diamond Push Up", R.drawable.diamond_push_up, {}),
            Exercise("dumbbellFly", "Dumbbell Fly", R.drawable.dumbbell_fly, {}),
            Exercise("highCableCrossover", "High Cable Crossover", R.drawable.high_cable_crossover, {}),
            Exercise("inclineDumbbellPress", "Incline Dumbbell Press", R.drawable.incline_dumbbell_press, {}),
            Exercise("pecDeckFly", "Pec Deck Fly", R.drawable.pec_deck_fly, {}),
            Exercise("pushUp", "Push Up", R.drawable.push_up, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Shoulders(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "shoulders",
        "Spalle",
        R.drawable.ic_shoulders,
        listOf(
            Exercise("alternatingDumbbellFrontRaise", "Alternating Dumbbell Front Raise", R.drawable.alternating_dumbbell_front_raise, {}),
            Exercise("dumbbellLateralRaise", "Dumbbell Lateral Raise", R.drawable.dumbbell_lateral_raise, {}),
            Exercise("dumbbellReverseFly", "Dumbbell Reverse Fly", R.drawable.dumbbell_reverse_fly, {}),
            Exercise("dumbbellShoulderPress", "Dumbbell Shoulder Press", R.drawable.dumbbell_shoulder_press, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Biceps(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "biceps",
        "Bicipiti",
        R.drawable.ic_biceps,
        listOf(
            Exercise("concentrationCurl", "Concentration Curl", R.drawable.concentration_curl, {}),
            Exercise("dumbbellCurl", "Dumbbell Curl", R.drawable.dumbbell_curl, {}),
            Exercise("hammerCurl", "Hammer Curl", R.drawable.hammer_curl, {}),
            Exercise("ropeBicepCurls", "Rope Bicep Curls", R.drawable.rope_bicep_curls, {}),
            Exercise("zBarPreacherCurl", "Z Bar Preacher Curl", R.drawable.z_bar_preacher_curl, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Triceps(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "triceps",
        "Tricipiti",
        R.drawable.ic_triceps,
        listOf(
            Exercise("barbellTricepsExtension", "Barbell Triceps Extension", R.drawable.barbell_triceps_extension, {}),
            Exercise("dumbbellKickback", "Dumbbell Kickback", R.drawable.dumbbell_kickback, {}),
            Exercise("pushDown", "Push Down", R.drawable.push_down, {}),
            Exercise("seatedDumbbellTricepsExtension", "Seated Dumbbell Triceps Extension", R.drawable.seated_dumbbell_triceps_extension, {}),
            Exercise("tricepsDips", "Triceps Dips", R.drawable.triceps_dips, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Abs(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "abs",
        "Addominali",
        R.drawable.ic_abs,
        listOf(
            Exercise("crossCrunch", "Cross Crunch", R.drawable.cross_crunch, {}),
            Exercise("crunch", "Crunch", R.drawable.crunch, {}),
            Exercise("plank", "Plank", R.drawable.plank, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Back(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "back",
        "Schiena",
        R.drawable.ic_back,
        listOf(
            Exercise("dumbbellRow", "Dumbbell Row", R.drawable.dumbbell_row, {}),
            Exercise("latPulldown", "Lat Pulldown", R.drawable.lat_pulldown, {}),
            Exercise("pullUp", "Pull Up", R.drawable.pull_up, {}),
            Exercise("tBarRow", "T Bar Row", R.drawable.t_bar_row, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Legs(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "legs",
        "Gambe",
        R.drawable.ic_legs,
        listOf(
            Exercise("barbellDeadlift", "Barbell Deadlift", R.drawable.barbell_deadlift, {}),
            Exercise("barbellRomanianDeadlift", "Barbell Romanian Deadlift", R.drawable.barbell_romanian_deadlift, {}),
            Exercise("barbellSquat", "Barbell Squat", R.drawable.barbell_squat, {}),
            Exercise("dumbbellBulgarianSplitSquat", "Dumbbell Bulgarian Split Squat", R.drawable.dumbbell_bulgarian_split_squat, {}),
            Exercise("dumbbellLunge", "Dumbbell Lunge", R.drawable.dumbbell_lunge, {}),
            Exercise("legCurl", "Leg Curl", R.drawable.leg_curl, {}),
            Exercise("legExtention", "Leg Extention", R.drawable.leg_extention, {}),
            Exercise("legPress", "Leg Press", R.drawable.leg_press, {}),
            Exercise("seatedLegCurl", "Seated Leg Curl", R.drawable.seated_leg_curl, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )
    data class Calves(
        override var onMuscleGroupClick: () -> Unit = {}
    ) : MuscleGroup(
        "calves",
        "Polpacci",
        R.drawable.ic_calves,
        listOf(
            Exercise("dumbbellCalfRaise", "Dumbbell Calf Raise", R.drawable.dumbbell_calf_raise, {}),
            Exercise("leverSeatedCalfRaise", "Lever Seated Calf Raise", R.drawable.lever_seated_calf_raise, {})
        ),
        onMuscleGroupClick = onMuscleGroupClick
    )

    companion object {
        fun getExercisesFromId(id: String?) : List<Exercise>{
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