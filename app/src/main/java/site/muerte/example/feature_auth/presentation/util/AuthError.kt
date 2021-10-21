package site.muerte.example.feature_auth.presentation.util

import site.muerte.example.core.util.Error

sealed class AuthError : Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
}
