package site.muerte.example.feature_auth.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import site.muerte.example.feature_auth.data.remote.request.AuthRequest
import site.muerte.example.feature_auth.data.remote.response.AuthResponse

interface AuthApi {

    @POST(REGISTER)
    suspend fun register(@Body request: AuthRequest): Response<AuthResponse>

    companion object {
        const val BASE_URL = "https://167c-94-45-205-222.ngrok.io/"
        const val REGISTER = "register"
    }
}