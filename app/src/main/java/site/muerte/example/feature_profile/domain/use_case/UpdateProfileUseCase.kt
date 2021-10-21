package site.muerte.example.feature_profile.domain.use_case

import site.muerte.example.core.util.SimpleResource
import site.muerte.example.feature_profile.domain.models.Profile
import site.muerte.example.feature_profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(profile: Profile): SimpleResource {
        return repository.updateProfile(profile)
    }

}