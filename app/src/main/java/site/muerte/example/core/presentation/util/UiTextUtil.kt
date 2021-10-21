package site.muerte.example.core.presentation.util

import android.content.Context
import site.muerte.example.core.util.UiText

fun UiText.asString(context: Context): String {
    return when(this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> context.getString(this.id)
    }
}