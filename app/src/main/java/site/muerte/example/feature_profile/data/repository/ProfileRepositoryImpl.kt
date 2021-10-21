package site.muerte.example.feature_profile.data.repository

import com.google.gson.JsonParser
import retrofit2.HttpException
import site.muerte.example.R
import site.muerte.example.core.util.Resource
import site.muerte.example.core.util.SimpleResource
import site.muerte.example.core.util.UiText
import site.muerte.example.feature_profile.data.remote.ProfileApi
import site.muerte.example.feature_profile.domain.models.City
import site.muerte.example.feature_profile.domain.models.Profile
import site.muerte.example.feature_profile.domain.repository.ProfileRepository
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun getProfile(): Resource<Profile> {
        return try {
            val response = api.getProfile()
            if (response.isSuccessful) {
                Resource.Success(response.body()?.toProfile())
            } else {
                response.message().let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                }
            }
        } catch (e: IOException) {
            Resource.Error(
                message = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                message = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun updateProfile(profile: Profile): SimpleResource {
        return try {
            val response = api.updateProfile(profile)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                var message = ""
                if (response.errorBody() != null) {
                    val error = response.errorBody()?.string()
                    if (error!!.contains("message", ignoreCase = true)) {
                        val jsonObject = JsonParser().parse(error).asJsonObject["message"]
                        if (!jsonObject.isJsonNull) {
                            message = jsonObject.asString
                        }
                    }
                }
                Resource.Error(UiText.DynamicString(message))
            }
        } catch (e: IOException) {
            Resource.Error(
                message = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                message = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getCities(): Resource<List<City>> {
        return try {
            val response = api.getCities()
            Resource.Success(
                data = response.body()
            )
        } catch (e: IOException) {
            Resource.Error(
                message = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                message = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

}