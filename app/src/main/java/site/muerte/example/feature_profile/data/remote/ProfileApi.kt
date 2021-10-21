package site.muerte.example.feature_profile.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import site.muerte.example.feature_profile.data.remote.response.ProfileResponse
import site.muerte.example.feature_profile.domain.models.City
import site.muerte.example.feature_profile.domain.models.Profile

interface ProfileApi {

    @GET(CITIES)
    suspend fun getCities(): Response<List<City>>

    @GET(PROFILE)
    suspend fun getProfile(): Response<ProfileResponse>

    @POST(PROFILE)
    suspend fun updateProfile(@Body profile: Profile): Response<Unit>

    companion object {
        const val BASE_URL = "https://167c-94-45-205-222.ngrok.io/"
        const val PROFILE = "profile"
        const val CITIES = "cities"
    }
}