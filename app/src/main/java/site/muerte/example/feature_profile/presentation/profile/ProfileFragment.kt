package site.muerte.example.feature_profile.presentation.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import site.muerte.example.R
import site.muerte.example.core.data.cache.SharedPrefsManager
import site.muerte.example.core.domain.states.ViewState
import site.muerte.example.core.presentation.util.asString
import site.muerte.example.core.presentation.util.ex.*
import site.muerte.example.databinding.FragmentProfileBinding
import site.muerte.example.feature_profile.domain.models.City
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var preferences: SharedPrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        initUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onBackPressedDispatcher()
        binding.lifecycleOwner = this
        binding.model = viewModel

        initElements()
        processData()

        return binding.root
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.etEmail.afterTextChanged {
            binding.etEmail.validateEmail(requireContext())
        }
        binding.tvEmailInfo.setOnClickListener {
            val args = Bundle()
            args.putString("info", "email")
            findNavController().navigate(R.id.infoFragment, args)
        }
        binding.etCity.setOnClickListener {
            showSelectDialog(viewModel.cities.value ?: emptyList())
        }
        binding.btnNext.setOnClickListener {
            if (viewModel.name.value.isNullOrEmpty() ||
                viewModel.surname.value.isNullOrEmpty() ||
                viewModel.middleName.value.isNullOrEmpty() ||
                viewModel.email.value.isNullOrEmpty() ||
                viewModel.city.value.isNullOrEmpty()
            ) {
                Toast.makeText(context, R.string.fill_fields, Toast.LENGTH_SHORT).show()
            } else if (!viewModel.email.value.isValidEmail()) {
                Toast.makeText(context, R.string.incorrect_email, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateProfile()
            }
        }
        binding.btnSupport.setOnClickListener {
            findNavController().navigate(R.id.supportFragment)
        }
    }

    private fun initObservers() {
        viewModel.navigateToNextPage.observe(this, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.introFirstFragment)
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

    private fun processData() {
        binding.etSurname.afterTextChanged {
            preferences.saveSurname(it)
        }
        binding.etName.afterTextChanged {
            preferences.saveName(it)
        }
        binding.etMiddleName.afterTextChanged {
            preferences.saveMiddleName(it)
        }
        binding.etEmail.afterTextChanged {
            preferences.saveEmail(it)
        }
    }

    private fun initElements() {
        binding.etPhone.setText(preferences.getPhoneDisplay())
        viewModel.name.value = preferences.getName()
        viewModel.surname.value = preferences.getSurname()
        viewModel.middleName.value = preferences.getMiddleName()
        viewModel.city.value = preferences.getCity()
        viewModel.cityId.value = preferences.getCityId()
        viewModel.email.value = preferences.getEmail()
    }

    private fun showSelectDialog(listFieldNames: List<City>) {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_searchable_spinner)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val title = dialog.findViewById<TextView>(R.id.tv_title)
        val editText = dialog.findViewById<EditText>(R.id.et_dialog)
        val listView = dialog.findViewById<ListView>(R.id.list_view)

        title.setText(R.string.choose_city)
        val listNames = ArrayList<String>()
        val listCities = listFieldNames.sortedBy { it.name }
        listCities.forEach {
            listNames.add(it.name)
        }

        val adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, listNames)
        listView.adapter = adapter
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, p2, _ -> // When item selected from list
                viewModel.cityId.value = listCities[p2].id
                viewModel.city.value = listCities[p2].name
                preferences.saveCity(viewModel.city.value!!)
                preferences.saveCityId(viewModel.cityId.value!!)
                dialog.dismiss()
            }
    }

}