package com.example.fitnessapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.R

@Composable
fun GroupBox(items: List<BoxItem>) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.DarkGray)

    ) {
        itemsIndexed(items) { index, item ->
            when (item.getType()) {
                1 -> BoxItem1(index == items.lastIndex, item, focusManager)
                2 -> BoxItem2(index == items.lastIndex, item, focusManager)
                3 -> BoxItem3(index == items.lastIndex, item)
            }
        }
    }
}

@Composable
fun BoxItem1(
    isLastItem: Boolean,
    item: BoxItem,
    focusManager: FocusManager
) {
    var isSaved by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp, start = 30.dp, end = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (item.content != null && item.content != "%i") {
            var content by remember { mutableStateOf(TextFieldValue(item.content!!)) }

            LaunchedEffect(isEnabled) {
                if (isEnabled) {
                    focusRequester.requestFocus()
                    content = TextFieldValue(
                        text = content.text,
                        selection = TextRange(content.text.length)
                    )
                }
            }

            BasicTextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (!it.isFocused && !isSaved) content = TextFieldValue(item.content!!)
                        isEnabled = it.isFocused
                        isSaved = false
                    }
                    .defaultMinSize(minWidth = 100.dp)
                    .focusRequester(focusRequester),
                value = content,
                cursorBrush = SolidColor(Color.White),
                enabled = isEnabled,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White, textAlign = TextAlign.Start, fontSize = 20.sp),
                onValueChange = {
                    if (item.keyboardType != BoxItem.KeyboardDate) {
                        content = it
                    }
                    else if (it.text.length <= 8) content = it
                },
                visualTransformation = if (item.keyboardType == BoxItem.KeyboardDate) DateTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = item.keyboardType?.getType()!!,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (content.text == "") {
                            content = TextFieldValue(item.content!!)
                        }
                        else if (item.keyboardType == BoxItem.KeyboardDate) {
                            if (content.text.length == 8) {
                                item.onSaveContent!!(content.text)
                                isSaved = true
                            }
                            else content = TextFieldValue(item.content!!)
                        }
                        else {
                            item.onSaveContent!!(content.text)
                            isSaved = true
                        }

                        focusManager.clearFocus()
                    }
                )
            )
            IconButton(
                onClick = {
                    isEnabled = true
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "edit",
                )
            }
        }
    }

    if (!isLastItem) {
        Divider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.LightGray,
            thickness = 0.25.dp
        )
    }
}

@Composable
fun BoxItem2(
    isLastItem: Boolean,
    item: BoxItem,
    focusManager: FocusManager
) {
    var isSaved by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("${item.title}")

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            if (item.content != null && item.content != "%i") {
                var content by remember { mutableStateOf(item.content) }

                BasicTextField(
                    modifier = Modifier
                        .onFocusChanged {
                            if (!it.isFocused && !isSaved) {
                                content = item.content
                            }

                            isSaved = false
                        }
                        .defaultMinSize(minWidth = 100.dp),
                    value = content!!,
                    cursorBrush = SolidColor(Color.White),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White, textAlign = TextAlign.End),
                    onValueChange = {
                        if (item.keyboardType == BoxItem.KeyboardDate) {
                            if (it.length <= 8) content = it
                        } else {
                            content = it
                        }
                    },
                    visualTransformation = if (item.keyboardType == BoxItem.KeyboardDate) DateTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = item.keyboardType?.getType()!!,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (content != "") {
                                if (item.keyboardType == BoxItem.KeyboardDate) {
                                    if (content!!.length == 8) {
                                         item.onSaveContent!!(content!!)
                                        isSaved = true
                                    }
                                    else content = item.content
                                }
                                else {
                                    item.onSaveContent!!(content!!)
                                    isSaved = true
                                }
                            } else content = item.content

                            focusManager.clearFocus()
                        }
                    )
                )

                if (item.unit != null) {
                    Spacer(Modifier.width(5.dp))
                    Text("${item.unit}")
                }
            }
        }
    }

    if (!isLastItem) {
        Divider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.LightGray,
            thickness = 0.25.dp
        )
    }
}

@Composable
fun BoxItem3(
    isLastItem: Boolean,
    item: BoxItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { item.onBoxItemClick!!() }
            .padding(15.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text("${item.title}")

        if (!isLastItem) {
            Divider(
                modifier = Modifier.padding(horizontal = 10.dp),
                color = Color.LightGray,
                thickness = 0.25.dp
            )
        }
    }
}

