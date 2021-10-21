package site.muerte.example.feature_profile.domain.models

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("city_id")
    var cityId: Int? = null,
    var city: String? = null,
    var email: String? = null,
    @SerializedName("is_completed")
    var isCompleted: Int? = null,
    @SerializedName("middle_name")
    var middleName: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var surname: String? = null
)
