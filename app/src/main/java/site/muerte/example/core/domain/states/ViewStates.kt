package site.muerte.example.core.domain.states

import site.muerte.example.core.util.UiText

data class ViewState(
    val isLoading: Boolean = false,
    val error: UiText = UiText.DynamicString("")
)