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
import com.example.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    showDatePicker: MutableState<Boolean>
) {
    TopAppBar(
        title = {
            Text("Home")
        },
        actions = {
            IconButton(onClick = {}) {
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