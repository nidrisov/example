package site.muerte.example.feature_auth.domain.use_case

import site.muerte.example.feature_auth.domain.models.LoginResult
import site.muerte.example.feature_auth.domain.repository.AuthRepository
import site.muerte.example.feature_auth.presentation.util.AuthError

class LoginPhoneUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(phone: String): LoginResult {
        val phoneError = if(phone.isBlank()) AuthError.FieldEmpty else null

        if(phoneError != null) {
            return LoginResult(phoneError)
        }

        return LoginResult(
            result = repository.loginPhone(phone = phone)
        )
    }
}