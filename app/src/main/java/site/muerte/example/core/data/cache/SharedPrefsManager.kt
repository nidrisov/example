package site.muerte.example.core.data.cache

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(private val prefs: SharedPreferences) {
    companion object {
        const val PHONE = "phone"
        const val PHONE_DISPLAY = "phone_display"
        const val IDN = "idn"
        const val TOKEN = "token"
        const val SURNAME = "surname"
        const val NAME = "name"
        const val MIDDLE_NAME = "middle_name"
        const val EMAIL = "email"
        const val CITY = "city"
        const val CITY_ID = "city_id"

        const val FIRST_LAUNCH = "first_launch"
        const val SHOW_REVIEW = "show_review"
    }

    fun saveFirstLaunch(value: Boolean) {
        prefs.edit().apply {
            putBoolean(FIRST_LAUNCH, value)
        }.apply()
    }

    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(FIRST_LAUNCH, true)
    }

    fun saveReview(value: Boolean) {
        prefs.edit().apply {
            putBoolean(SHOW_REVIEW, value)
        }.apply()
    }

    fun showReview(): Boolean {
        return prefs.getBoolean(SHOW_REVIEW, true)
    }

    fun saveIdn(idn: String) {
        prefs.edit().apply {
            putString(IDN, idn)
        }.apply()
    }

    fun getIdn(): String? {
        return prefs.getString(IDN, "")
    }

    fun savePhone(phone: String) {
        prefs.edit().apply {
            putString(PHONE, phone)
        }.apply()
    }

    fun getPhoneDisplay(): String? {
        return prefs.getString(PHONE_DISPLAY, "")
    }

    fun savePhoneDisplay(phone: String) {
        prefs.edit().apply {
            putString(PHONE_DISPLAY, phone)
        }.apply()
    }

    fun getPhone(): String? {
        return prefs.getString(PHONE, "")
    }


    fun saveToken(token: String) {
        prefs.edit().apply {
            putString(TOKEN, token)
        }.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN, "")
    }

    fun saveSurname(surname: String) {
        prefs.edit().apply {
            putString(SURNAME, surname)
        }.apply()
    }

    fun getSurname(): String? {
        return prefs.getString(SURNAME, "")
    }

    fun saveName(name: String) {
        prefs.edit().apply {
            putString(NAME, name)
        }.apply()
    }

    fun getName(): String? {
        return prefs.getString(NAME, "")
    }

    fun saveMiddleName(name: String) {
        prefs.edit().apply {
            putString(MIDDLE_NAME, name)
        }.apply()
    }

    fun getMiddleName(): String? {
        return prefs.getString(MIDDLE_NAME, "")
    }

    fun saveCity(city: String) {
        prefs.edit().apply {
            putString(CITY, city)
        }.apply()
    }

    fun getCity(): String? {
        return prefs.getString(CITY, "")
    }

    fun saveCityId(cityId: Int) {
        prefs.edit().apply {
            putInt(CITY_ID, cityId)
        }.apply()
    }

    fun getCityId(): Int {
        return prefs.getInt(CITY_ID, 1)
    }

    fun saveEmail(email: String) {
        prefs.edit().apply {
            putString(EMAIL, email)
        }.apply()
    }

    fun getEmail(): String? {
        return prefs.getString(EMAIL, "")
    }

}