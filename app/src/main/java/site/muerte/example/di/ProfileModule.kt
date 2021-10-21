package site.muerte.example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import site.muerte.example.feature_profile.data.remote.ProfileApi
import site.muerte.example.feature_profile.data.repository.ProfileRepositoryImpl
import site.muerte.example.feature_profile.domain.repository.ProfileRepository
import site.muerte.example.feature_profile.domain.use_case.GetCitiesUseCase
import site.muerte.example.feature_profile.domain.use_case.GetProfileUseCase
import site.muerte.example.feature_profile.domain.use_case.ProfileUseCases
import site.muerte.example.feature_profile.domain.use_case.UpdateProfileUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            getCities = GetCitiesUseCase(repository)
        )
    }
}