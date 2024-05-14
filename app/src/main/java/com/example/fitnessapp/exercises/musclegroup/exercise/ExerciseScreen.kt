package com.example.fitnessapp.exercises.musclegroup.exercise

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.example.fitnessapp.R

@Composable
fun ExerciseScreen(
    navController: NavController,
    muscleGroupId: String?,
    exerciseId: String?
) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(context))
            } else {
                add(GifDecoder())
            }
        }
        .build()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                imageLoader = imageLoader,
                data = Exercise.getGifResourceFromId(exerciseId = exerciseId),
                builder = {
                    size(OriginalSize)
                }
            ),
            contentDescription = null
        )
    }
}