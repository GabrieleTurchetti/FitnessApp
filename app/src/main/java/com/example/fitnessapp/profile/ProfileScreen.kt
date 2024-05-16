package com.example.fitnessapp.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessapp.common.BoxItem
import com.example.fitnessapp.common.BoxItem1
import com.example.fitnessapp.common.BoxItem2
import com.example.fitnessapp.common.GroupBox
import com.example.fitnessapp.datastore.ProfileSettings
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = ProfileSettings(context)
    val savedUsername = dataStore.getUsername.collectAsState(initial = "%i")
    val savedBirthDate = dataStore.getBirthDate.collectAsState(initial = "%i")
    val savedGender = dataStore.getGender.collectAsState(initial = "%i")
    val savedHeight = dataStore.getHeight.collectAsState(initial = "%i")
    val savedWeight = dataStore.getWeight.collectAsState(initial = "%i")
    val savedStepGoal = dataStore.getStepGoal.collectAsState(initial = "%i")
    var username by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var stepGoal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        GroupBox(items = listOf(
            BoxItem1(content = savedUsername.value!!)
        ))
        Spacer(modifier = Modifier.height(20.dp))
        GroupBox(items = listOf(
            BoxItem2(title = "Data di nascita", content = savedBirthDate.value!!, keyboardType = BoxItem.KeyboardDate, onSaveContent = {
                scope.launch {
                    dataStore.setBirthDate(it)
                }
            }),
            BoxItem2(title = "Sesso", content = savedGender.value!!, keyboardType = BoxItem.KeyboardGender, onSaveContent = {
                scope.launch {
                    dataStore.setGender(it)
                }
            }),
            BoxItem2(title = "Altezza", content = savedHeight.value!!, unit = "cm", keyboardType = BoxItem.KeyboardNumber, onSaveContent = {
                scope.launch {
                    dataStore.setHeight(it)
                }
            }),
            BoxItem2(title = "Peso", content = savedWeight.value!!, unit = "kg", keyboardType = BoxItem.KeyboardNumber, onSaveContent = {
                scope.launch {
                    dataStore.setWeight(it)
                }
            })
        ))
        Spacer(modifier = Modifier.height(20.dp))
        GroupBox(items = listOf(
            BoxItem2(title = "Obbiettivo giornaliero", content = savedStepGoal.value!!, keyboardType = BoxItem.KeyboardNumber, onSaveContent = {
                scope.launch {
                    dataStore.setStepGoal(it)
                }
            })
        ))
    }
}