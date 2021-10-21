package site.muerte.example.feature_auth.domain.repository

import site.muerte.example.core.util.Resource
import site.muerte.example.feature_auth.data.remote.response.AuthResponse

interface AuthRepository {

    suspend fun loginPhone(
        phone: String
    ): Resource<AuthResponse>

    suspend fun loginCode(
        phone: String,
        idn: String,
        code: Int
    ): Resource<AuthResponse>

}