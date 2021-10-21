package site.muerte.example.feature_auth.presentation.login_phone

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
import site.muerte.example.feature_auth.domain.use_case.LoginPhoneUseCase
import javax.inject.Inject

@HiltViewModel
class LoginPhoneViewModel @Inject constructor(
    private val loginPhoneUseCase: LoginPhoneUseCase,
    private val preferences: SharedPrefsManager
) : ViewModel() {
    private val _navigateToLoginCode = MutableLiveData<Event<Boolean>>()
    val navigateToLoginCode: LiveData<Event<Boolean>>
        get() = _navigateToLoginCode

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState>
        get() = _viewState

    fun loginPhone(phone: String) {
        viewModelScope.launch {
            _viewState.value = ViewState(isLoading = true)
            val loginResult = loginPhoneUseCase(phone = phone)
            _viewState.value = ViewState(isLoading = false)
            when (loginResult.result) {
                is Resource.Error -> {
                    _viewState.value =
                        ViewState(error = loginResult.result.message ?: UiText.unknownError())
                }
                is Resource.Success -> {
                    preferences.saveIdn(loginResult.result.data!!.idn!!)
                    _navigateToLoginCode.value = Event(true)
                }
            }
        }
    }

}