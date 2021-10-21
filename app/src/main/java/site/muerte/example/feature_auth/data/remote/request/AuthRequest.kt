package site.muerte.example.feature_auth.data.remote.request

data class AuthRequest(
    val phone: String? = null,
    val idn: String? = null,
    val code: Int? = null
)