package site.muerte.example.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import site.muerte.example.feature_auth.data.remote.AuthApi
import site.muerte.example.feature_auth.data.repository.AuthRepositoryImpl
import site.muerte.example.feature_auth.domain.repository.AuthRepository
import site.muerte.example.feature_auth.domain.use_case.LoginCodeUseCase
import site.muerte.example.feature_auth.domain.use_case.LoginPhoneUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sharedPreferences: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideLoginPhoneUseCase(repository: AuthRepository): LoginPhoneUseCase {
        return LoginPhoneUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginCodeUseCase(repository: AuthRepository): LoginCodeUseCase {
        return LoginCodeUseCase(repository)
    }

}