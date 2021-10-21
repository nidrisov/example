package site.muerte.example.feature_auth.presentation.login_code

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
import site.muerte.example.core.domain.states.ViewState
import site.muerte.example.core.presentation.util.asString
import site.muerte.example.core.presentation.util.ex.hideKeyboard
import site.muerte.example.core.presentation.util.ex.toast
import site.muerte.example.databinding.FragmentLoginCodeBinding

@AndroidEntryPoint
class LoginCodeFragment : Fragment() {

    private lateinit var binding: FragmentLoginCodeBinding
    private val viewModel: LoginCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginCodeBinding.inflate(layoutInflater)
        initUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.btnNext.setOnClickListener {
            val code = binding.inputCode.text.toString().replace(" ", "")
            if (code.length < 4) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.toast_incorrect_code),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.loginCode(code.toInt())
            }
        }
        binding.btnSendAgain.setOnClickListener {
            viewModel.sendCodeAgain()
        }
        binding.btnSupport.setOnClickListener {
            findNavController().navigate(R.id.supportFragment)
        }

        val listener = MaskedTextChangedListener.installOn(
            binding.inputCode,
            "[0000]",
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
        binding.inputCode.hint = listener.placeholder()

    }

    private fun initObservers() {
        viewModel.navigateToProfile.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.profileFragment)
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