package site.muerte.example.feature_auth.presentation.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import site.muerte.example.R
import site.muerte.example.core.data.cache.SharedPrefsManager
import site.muerte.example.databinding.FragmentIntroThirdBinding
import javax.inject.Inject

@AndroidEntryPoint
class IntroThirdFragment : Fragment() {

    private var _binding: FragmentIntroThirdBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferences: SharedPrefsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroThirdBinding.inflate(inflater, container, false)
        binding.btnNext.setOnClickListener {
            preferences.saveFirstLaunch(false)
            findNavController().navigate(R.id.loginPhoneFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}