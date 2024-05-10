package com.example.fitnessapp.exercises

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import com.example.fitnessapp.CardCell
import com.example.fitnessapp.R

sealed class MuscleGroup(
    val name: String,
    @DrawableRes val imageId: Int,
    val exercises : List<Exercise>
) {
    object Chest : MuscleGroup(
        "Pettorali",
        R.drawable.chest,
        listOf(
            Exercise("Barbell Bench Press", R.drawable.barbell_bench_press),
            Exercise("Diamond Push Up", R.drawable.diamond_push_up),
            Exercise("Dumbbell Fly", R.drawable.dumbbell_fly),
            Exercise("High Cable Crossover", R.drawable.high_cable_crossover),
            Exercise("Incline Dumbbell Press", R.drawable.incline_dumbbell_press),
            Exercise("Pec Deck Fly", R.drawable.pec_deck_fly),
            Exercise("Push Up", R.drawable.push_up)
        )
    )
    object Shoulders : MuscleGroup(
        "Spalle",
        R.drawable.shoulders,
        listOf(
            Exercise("Alternating Dumbbell Front Raise", R.drawable.alternating_dumbbell_front_raise),
            Exercise("Dumbbell Lateral Raise", R.drawable.dumbbell_lateral_raise),
            Exercise("Dumbbell Reverse Fly", R.drawable.dumbbell_reverse_fly),
            Exercise("Dumbbell Shoulder Press", R.drawable.dumbbell_shoulder_press)
        )
    )
    object Biceps : MuscleGroup(
        "Bicipiti",
        R.drawable.biceps,
        listOf(
            Exercise("Concentration Curl", R.drawable.concentration_curl),
            Exercise("Dumbbell Curl", R.drawable.dumbbell_curl),
            Exercise("Hammer Curl", R.drawable.hammer_curl),
            Exercise("Rope Bicep Curls", R.drawable.rope_bicep_curls),
            Exercise("Z Bar Preacher Curl", R.drawable.z_bar_preacher_curl)
        )
    )
    object Triceps : MuscleGroup(
        "Tricipiti",
        R.drawable.triceps,
        listOf(
            Exercise("Barbell Triceps Extension", R.drawable.barbell_triceps_extension),
            Exercise("Dumbbell Kickback", R.drawable.dumbbell_kickback),
            Exercise("Push Down", R.drawable.push_down),
            Exercise("Seated Dumbbell Triceps Extension", R.drawable.seated_dumbbell_triceps_extension),
            Exercise("Triceps Dips", R.drawable.triceps_dips)
        )
    )
    object Abs : MuscleGroup(
        "Addominali",
        R.drawable.abs,
        listOf(
            Exercise("Cross Crunch", R.drawable.cross_crunch),
            Exercise("Crunch", R.drawable.crunch),
            Exercise("Plank", R.drawable.plank)
        )
    )
    object Back : MuscleGroup(
        "Schiena",
        R.drawable.back,
        listOf(
            Exercise("Dumbbell Row", R.drawable.dumbbell_row),
            Exercise("Lat Pulldown", R.drawable.lat_pulldown),
            Exercise("Pull Up", R.drawable.pull_up),
            Exercise("T Bar Row", R.drawable.t_bar_row)
        )
    )
    object Legs : MuscleGroup(
        "Gambe",
        R.drawable.legs,
        listOf(
            Exercise("Barbell Deadlift", R.drawable.barbell_deadlift),
            Exercise("Barbell Romanian Deadlift", R.drawable.barbell_romanian_deadlift),
            Exercise("Barbell Squat", R.drawable.barbell_squat),
            Exercise("Dumbbell Bulgarian Split Squat", R.drawable.dumbbell_bulgarian_split_squat),
            Exercise("Dumbbell Lunge", R.drawable.dumbbell_lunge),
            Exercise("Leg Curl", R.drawable.leg_curl),
            Exercise("Leg Extention", R.drawable.leg_extention),
            Exercise("Leg Press", R.drawable.leg_press),
            Exercise("Seated Leg Curl", R.drawable.seated_leg_curl)
        )
    )
    object Calves : MuscleGroup(
        "Polpacci",
        R.drawable.calves,
        listOf(
            Exercise("Dumbbell Calf Raise", R.drawable.dumbbell_calf_raise),
            Exercise("Lever Seated Calf Raise", R.drawable.lever_seated_calf_raise)
        )
    )

    fun toCardCell(): CardCell {
        return CardCell(
            name,
            imageId,
            {}
        )
    }
}