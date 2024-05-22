package com.example.fitnessapp.ui.theme.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardGrid(cards: List<CardItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        items(cards.size) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    cards[it].onCardItemClick()
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .background(Color.White),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        painter = painterResource(cards[it].imageId),
                        contentDescription = cards[it].name,
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(cards[it].name)
                    }
                }
            }
        }
    }
}