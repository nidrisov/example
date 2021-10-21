package site.muerte.example.feature_profile.domain.repository

import site.muerte.example.core.util.Resource
import site.muerte.example.core.util.SimpleResource
import site.muerte.example.feature_profile.domain.models.City
import site.muerte.example.feature_profile.domain.models.Profile

interface ProfileRepository {

    suspend fun getProfile(): Resource<Profile>

    suspend fun updateProfile(profile: Profile): SimpleResource

    suspend fun getCities(): Resource<List<City>>

}