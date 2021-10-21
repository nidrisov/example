package site.muerte.example.feature_profile.domain.use_case

import site.muerte.example.core.util.Resource
import site.muerte.example.feature_profile.domain.models.Profile
import site.muerte.example.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<Profile> {
        return repository.getProfile()
    }

}