package site.muerte.example.feature_profile.domain.use_case

import site.muerte.example.core.util.Resource
import site.muerte.example.feature_profile.domain.models.City
import site.muerte.example.feature_profile.domain.repository.ProfileRepository

class GetCitiesUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<List<City>> {
        return repository.getCities()
    }

}