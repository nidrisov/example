package site.muerte.example.feature_auth.domain.models

import site.muerte.example.core.util.Resource
import site.muerte.example.core.util.SimpleResource
import site.muerte.example.feature_auth.data.remote.response.AuthResponse
import site.muerte.example.feature_auth.presentation.util.AuthError

data class LoginResult(
    val phoneError: AuthError? = null,
    val idnError: AuthError? = null,
    val codeError: AuthError? = null,
    val result: Resource<AuthResponse>? = null
)
