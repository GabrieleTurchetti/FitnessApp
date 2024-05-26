package com.example.fitnessapp.profile

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.ui.common.BoxItem
import com.example.fitnessapp.ui.common.BoxItem1
import com.example.fitnessapp.ui.common.BoxItem2
import com.example.fitnessapp.ui.common.GroupBox
import com.example.fitnessapp.datastore.ProfileSettings
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = ProfileSettings(context)
    val username = dataStore.getUsername.collectAsState(initial = "")
    val birthDate = dataStore.getBirthDate.collectAsState(initial = "")
    val gender = dataStore.getGender.collectAsState(initial = -1)
    val height = dataStore.getHeight.collectAsState(initial = -1)
    val weight = dataStore.getWeight.collectAsState(initial = -1)
    val stepGoal = dataStore.getStepGoal.collectAsState(initial = -1)

    Scaffold(
        topBar = {
            ProfileTopAppBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(20.dp)
        ) {
            GroupBox(
                items = listOf(
                    BoxItem1(
                        content = username.value,
                        onSaveContent = {
                            scope.launch {
                                dataStore.saveUsername(it)
                            }
                        },
                        isValid = { content ->
                            content != ""
                        }
                    )
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            GroupBox(
                items = listOf(
                    BoxItem2(
                        title = "Data di nascita",
                        content = birthDate.value,
                        keyboardType = BoxItem.KeyboardDate,
                        onSaveContent = {
                            scope.launch {
                                dataStore.saveBirthDate(it)
                            }
                        },
                        isValid = {
                            val day = it.substring(0, 2)
                            val month = it.substring(2, 4)
                            val year = (it.substring(4).toInt() + 7).toString()
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy");
                            val date = dateFormat.parse("$day/$month/$year");
                            Date().after(date) && it.length == 8
                        }
                    ),
                    BoxItem2(
                        title = "Sesso",
                        content = gender.value,
                        keyboardType = BoxItem.KeyboardGender,
                        onSaveContent = {
                            scope.launch {
                                dataStore.saveGender(it)
                            }
                        },
                        isValid = {
                            it != ""
                        }
                    ),
                    BoxItem2(
                        title = "Altezza",
                        content = height.value,
                        unit = "cm",
                        keyboardType = BoxItem.KeyboardNumber,
                        onSaveContent = {
                            scope.launch {
                                dataStore.saveHeight(it.toInt())
                            }
                        },
                        isValid = {
                            it.toInt() in 30..300
                        }
                    ),
                    BoxItem2(
                        title = "Peso",
                        content = weight.value,
                        unit = "kg",
                        keyboardType = BoxItem.KeyboardNumber,
                        onSaveContent = {
                            scope.launch {
                                dataStore.saveWeight(it.toInt())
                            }
                        },
                        isValid = {
                            it.toInt() in 30..300
                        }
                    )
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            GroupBox(
                items = listOf(
                    BoxItem2(
                        title = "Obbiettivo giornaliero",
                        content = stepGoal.value,
                        keyboardType = BoxItem.KeyboardNumber,
                        onSaveContent = {
                            scope.launch {
                                dataStore.saveStepGoal(it.toInt())
                            }
                        },
                        isValid = {
                            it.toInt() in 100..1000000
                        }
                    )
                )
            )
        }
    }
}