package site.muerte.example.feature_auth.presentation.login_phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import site.muerte.example.R
import site.muerte.example.core.data.cache.SharedPrefsManager
import site.muerte.example.core.domain.states.ViewState
import site.muerte.example.core.presentation.util.asString
import site.muerte.example.core.presentation.util.ex.hideKeyboard
import site.muerte.example.core.presentation.util.ex.onBackPressedDispatcher
import site.muerte.example.core.presentation.util.ex.toast
import site.muerte.example.databinding.FragmentLoginPhoneBinding
import javax.inject.Inject

@AndroidEntryPoint
class LoginPhoneFragment : Fragment() {

    private lateinit var binding: FragmentLoginPhoneBinding
    private val viewModel: LoginPhoneViewModel by viewModels()

    @Inject
    lateinit var preferences: SharedPrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginPhoneBinding.inflate(layoutInflater)
        initUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onBackPressedDispatcher()
        return binding.root
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.btnNext.setOnClickListener {
            val phoneDisplay = binding.inputPhone.text.toString()
            val phone = phoneDisplay.replace(Regex("[^0-9]"), "")
            if (phone.length != 11) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_number),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                preferences.savePhoneDisplay(phoneDisplay)
                preferences.savePhone(phone)
                viewModel.loginPhone(phone)
            }
        }
        binding.btnSupport.setOnClickListener {
            findNavController().navigate(R.id.supportFragment)
        }

        val listener = MaskedTextChangedListener.installOn(
            binding.inputPhone,
            "+7 9[00] [000] [00] [00]",
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    if (maskFilled) {
                        requireView().hideKeyboard()
                    }
                }
            }
        )
        binding.inputPhone.hint = listener.placeholder()

    }

    private fun initObservers() {
        viewModel.navigateToLoginCode.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.loginCodeFragment)
            }
        })

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    private fun updateUI(viewState: ViewState) {
        binding.progressBar.isVisible = viewState.isLoading
        val error = viewState.error.asString(requireContext())
        if (error.isNotBlank()) {
            toast(error)
        }
    }

}