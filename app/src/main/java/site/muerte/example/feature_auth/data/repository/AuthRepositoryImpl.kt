package site.muerte.example.feature_auth.data.repository

import android.content.SharedPreferences
import com.google.gson.JsonParser
import retrofit2.HttpException
import site.muerte.example.R
import site.muerte.example.core.util.Constants
import site.muerte.example.core.util.Resource
import site.muerte.example.core.util.UiText
import site.muerte.example.feature_auth.data.remote.AuthApi
import site.muerte.example.feature_auth.data.remote.request.AuthRequest
import site.muerte.example.feature_auth.data.remote.response.AuthResponse
import site.muerte.example.feature_auth.domain.repository.AuthRepository
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {
    override suspend fun loginPhone(phone: String): Resource<AuthResponse> {
        val request = AuthRequest(phone = phone)
        return try {
            val response = api.register(request)
            if (response.isSuccessful) {
                Resource.Success<AuthResponse>(response.body())
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
                Resource.Error<AuthResponse>(UiText.DynamicString(message))
            }
        } catch (e: IOException) {
            Resource.Error<AuthResponse>(
                message = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error<AuthResponse>(
                message = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun loginCode(phone: String, idn: String, code: Int): Resource<AuthResponse> {
        val request = AuthRequest(phone = phone, idn = idn, code = code)
        return try {
            val response = api.register(request)
            if (response.isSuccessful) {
                response.body()?.token.let { token ->
                    sharedPreferences.edit()
                        .putString(Constants.KEY_TOKEN, token)
                        .apply()
                }
                Resource.Success<AuthResponse>(response.body())
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
                Resource.Error<AuthResponse>(UiText.DynamicString(message))
            }
        } catch (e: IOException) {
            Resource.Error<AuthResponse>(
                message = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error<AuthResponse>(
                message = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }


}