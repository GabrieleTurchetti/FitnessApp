package com.example.fitnessapp.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.components.BoxItem
import com.example.fitnessapp.components.GroupBox
import com.example.fitnessapp.datastore.ProfileSettings
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = ProfileSettings(context)
    val savedUsername = dataStore.getUsername.collectAsState(initial = "")
    val savedBirthDate = dataStore.getBirthDate.collectAsState(initial = "")
    val savedGender = dataStore.getGender.collectAsState(initial = "")
    val savedHeight = dataStore.getHeight.collectAsState(initial = "")
    val savedWeight = dataStore.getWeight.collectAsState(initial = "")
    val savedStepGoal = dataStore.getStepGoal.collectAsState(initial = "")
    var username by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var stepGoal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        GroupBox(items = listOf(
            BoxItem(title = savedUsername.value!!, pencil = true)
        ))
        Spacer(modifier = Modifier.height(20.dp))
        GroupBox(items = listOf(
            BoxItem(title = "Data di nascita", content = savedBirthDate.value!!, onBoxItemClick = {
                scope.launch {
                    dataStore.setBirthDate(it)
                }
            }),
            BoxItem(title = "Sesso", content = savedGender.value!!, onBoxItemClick = {
                scope.launch {
                    dataStore.setGender(it)
                }
            }),
            BoxItem(title = "Altezza", content = savedHeight.value!!, onBoxItemClick = {
                scope.launch {
                    dataStore.setHeight(it)
                }
            }),
            BoxItem(title = "Peso", content = savedWeight.value!!, onBoxItemClick = {
                scope.launch {
                    dataStore.setWeight(it)
                }
            })
        ))
        Spacer(modifier = Modifier.height(20.dp))
        GroupBox(items = listOf(
            BoxItem(title = "Obbiettivo giornaliero", content = savedStepGoal.value!!, onBoxItemClick = {
                scope.launch {
                    dataStore.setStepGoal(it)
                }
            })
        ))
    }
}