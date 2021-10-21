package site.muerte.example.feature_auth.presentation.login_code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import site.muerte.example.core.data.cache.SharedPrefsManager
import site.muerte.example.core.domain.states.ViewState
import site.muerte.example.core.util.Event
import site.muerte.example.core.util.Resource
import site.muerte.example.core.util.UiText
import site.muerte.example.feature_auth.domain.use_case.LoginCodeUseCase
import site.muerte.example.feature_auth.domain.use_case.LoginPhoneUseCase
import javax.inject.Inject

@HiltViewModel
class LoginCodeViewModel @Inject constructor(
    private val loginCodeUseCase: LoginCodeUseCase,
    private val loginPhoneUseCase: LoginPhoneUseCase,
    private val preferences: SharedPrefsManager
) : ViewModel() {

    private val _navigateToProfile = MutableLiveData<Event<Boolean>>()
    val navigateToProfile: LiveData<Event<Boolean>>
        get() = _navigateToProfile

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState>
        get() = _viewState

    fun loginCode(code: Int) {
        viewModelScope.launch {
            _viewState.value = viewState.value.copy(isLoading = true)
            val loginResult = loginCodeUseCase(
                phone = preferences.getPhone()!!,
                idn = preferences.getIdn()!!,
                code = code
            )
            _viewState.value = viewState.value.copy(isLoading = false)
            when (loginResult.result) {
                is Resource.Error -> {
                    _viewState.value =
                        viewState.value.copy(
                            error = loginResult.result.message ?: UiText.unknownError()
                        )
                }
                is Resource.Success -> {
                    _viewState.value = ViewState()
                    preferences.saveToken(loginResult.result.data!!.token!!)
                    _navigateToProfile.value = Event(true)
                }
            }
        }
    }

    fun sendCodeAgain() {
        viewModelScope.launch {
            _viewState.value = ViewState(isLoading = true)
            val loginResult = loginPhoneUseCase(phone = preferences.getPhone()!!)
            _viewState.value = ViewState(isLoading = false)
            when (loginResult.result) {
                is Resource.Error -> {
                    _viewState.value =
                        viewState.value.copy(
                            error = loginResult.result.message ?: UiText.unknownError()
                        )
                }
                is Resource.Success -> {
                    _viewState.value = ViewState()
                    preferences.saveToken(loginResult.result.data!!.idn!!)
                }
            }
        }
    }

}