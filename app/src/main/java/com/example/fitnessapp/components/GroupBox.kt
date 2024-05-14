package com.example.fitnessapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun GroupBox(items: List<BoxItem>) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(items) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${item.title}")
                var isSaved by remember { mutableStateOf(false) }

                if (item.content != null && item.content != "") {
                    var content by remember { mutableStateOf(item.content) }

                    BasicTextField(
                        modifier = Modifier
                            .onFocusChanged {
                                if (!it.isFocused && !isSaved) {
                                    content = item.content
                                }

                                isSaved = false
                            },
                        value = content,
                        onValueChange = {
                            content = it
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (content != "") {
                                    item.onBoxItemClick(content)
                                    isSaved = true
                                }
                                else {
                                    content = item.content
                                }

                                focusManager.clearFocus()
                            }
                        )
                    )
                }
                else {
                    if (item?.pencil ?: false) {
                        Text("P")
                    }
                }
            }

            if (index < items.lastIndex)
                Divider(color = Color.White, thickness = 1.dp)
        }
    }
}