package site.muerte.example.feature_profile.data.remote.response

import com.google.gson.annotations.SerializedName
import site.muerte.example.feature_profile.domain.models.Profile

data class ProfileResponse(
    @SerializedName("city_id")
    val cityId: Int? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("is_completed")
    val isCompleted: Int? = null,
    @SerializedName("middle_name")
    val middleName: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("surname")
    val surname: String? = null
) {
    fun toProfile(): Profile {
        return Profile(
            cityId = cityId,
            email = email,
            isCompleted = isCompleted,
            middleName = middleName,
            name = name,
            phone = phone,
            surname = surname,
            city = city
        )
    }
}
