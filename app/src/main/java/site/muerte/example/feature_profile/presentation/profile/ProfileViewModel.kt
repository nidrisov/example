package site.muerte.example.feature_profile.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import site.muerte.example.core.domain.states.ViewState
import site.muerte.example.core.util.Event
import site.muerte.example.core.util.Resource
import site.muerte.example.core.util.UiText
import site.muerte.example.feature_profile.domain.models.City
import site.muerte.example.feature_profile.domain.models.Profile
import site.muerte.example.feature_profile.domain.use_case.ProfileUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _navigateToNextPage = MutableLiveData<Event<Boolean>>()
    val navigateToNextPage: LiveData<Event<Boolean>>
        get() = _navigateToNextPage

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState>
        get() = _viewState

    var cities: MutableLiveData<List<City>> = MutableLiveData()

    var name: MutableLiveData<String> = MutableLiveData()
    var surname: MutableLiveData<String> = MutableLiveData()
    var middleName: MutableLiveData<String> = MutableLiveData()
    var city: MutableLiveData<String> = MutableLiveData()
    var cityId: MutableLiveData<Int> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            when (val result = profileUseCases.getProfile()) {
                is Resource.Success -> {
                    name.value = result.data?.name
                    surname.value = result.data?.surname
                    middleName.value = result.data?.middleName
                    email.value = result.data?.email
                    city.value = result.data?.city
                    cityId.value = result.data?.cityId
                }
                is Resource.Error -> {
                }
            }
            when (val result = profileUseCases.getCities()) {
                is Resource.Success -> {
                    cities.value = result.data
                }
                is Resource.Error -> {
                }
            }
        }
    }


    fun updateProfile() {
        val profile = Profile(
            name = name.value,
            surname = surname.value,
            middleName = middleName.value,
            cityId = cityId.value,
            email = email.value
        )
        viewModelScope.launch {
            _viewState.value = ViewState(isLoading = true)
            val result = profileUseCases.updateProfile(profile)
            _viewState.value = ViewState(isLoading = false)
            when (result) {
                is Resource.Error -> {
                    _viewState.value =
                        ViewState(error = result.message ?: UiText.unknownError())
                }
                is Resource.Success -> {
                    _navigateToNextPage.value = Event(true)
                }
            }
        }
    }

}