package com.example.fitnessapp.ui.common

import androidx.compose.ui.text.input.KeyboardType

abstract class BoxItem(
    open val title: String? = null,
    open val content: Any,
    open val unit: String? = null,
    open val keyboardType: BoxItemKeyboardType = KeyboardText,
    open val onSaveContent: (String) -> Unit,
    open val isValid: (String) -> Boolean
) {
    abstract fun getType(): Int

    class BoxItemKeyboardType {
        fun getType(): KeyboardType {
            return when (this) {
                KeyboardText -> KeyboardType.Text
                KeyboardNumber -> KeyboardType.Number
                KeyboardDate -> KeyboardType.Number
                KeyboardGender -> KeyboardType.Text
                else -> KeyboardType.Text
            }
        }
    }

    companion object {
        val KeyboardText: BoxItemKeyboardType = BoxItemKeyboardType()
        val KeyboardNumber: BoxItemKeyboardType = BoxItemKeyboardType()
        val KeyboardDate: BoxItemKeyboardType = BoxItemKeyboardType()
        val KeyboardGender: BoxItemKeyboardType = BoxItemKeyboardType()
    }
}
class BoxItem1(
    override val content: Any,
    override val keyboardType: BoxItemKeyboardType = KeyboardText,
    override val onSaveContent: (String) -> Unit,
    override val isValid: (String) -> Boolean
) : BoxItem(
    content = content,
    keyboardType = keyboardType,
    onSaveContent = onSaveContent,
    isValid = isValid
) {
    override fun getType(): Int {
        return 1
    }
}

class BoxItem2(
    override val title: String,
    override val content: Any,
    override val unit: String? = null,
    override val keyboardType: BoxItemKeyboardType = KeyboardText,
    override val onSaveContent: (String) -> Unit,
    override val isValid: (String) -> Boolean
) : BoxItem(
    content = content,
    unit = unit,
    keyboardType = keyboardType,
    onSaveContent = onSaveContent,
    isValid = isValid
) {
    override fun getType(): Int {
        return 2
    }
}