package com.example.fitnessapp.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    showDatePicker: MutableState<Boolean>,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text("Home")
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.Map.route)
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_map),
                    contentDescription = "map",
                )
            }
            IconButton(onClick = {
                showDatePicker.value = true
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_calendar_month),
                    contentDescription = "calendar",
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer)
    )
}