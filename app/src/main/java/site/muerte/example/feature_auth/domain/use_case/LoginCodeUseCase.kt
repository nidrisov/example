package site.muerte.example.feature_auth.domain.use_case

import site.muerte.example.feature_auth.domain.models.LoginResult
import site.muerte.example.feature_auth.domain.repository.AuthRepository
import site.muerte.example.feature_auth.presentation.util.AuthError

class LoginCodeUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(phone: String, idn: String, code: Int): LoginResult {
        val phoneError = if(phone.isBlank()) AuthError.FieldEmpty else null
        val idnError = if(idn.isBlank()) AuthError.FieldEmpty else null

        if(phoneError != null || idnError != null) {
            return LoginResult(phoneError, idnError)
        }

        return LoginResult(
            result = repository.loginCode(phone = phone, idn = idn, code = code)
        )
    }

}