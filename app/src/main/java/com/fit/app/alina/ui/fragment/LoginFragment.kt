package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.app.alina.databinding.FragmentLoginBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initViewModel()
        initViews()
        return binding.root
    }

    private fun initViewModel() {
        val parentActivity = activity as MainActivity
        loginViewModel = parentActivity.loginViewModel
    }

    private fun initViews() {
        binding.loginButton.setOnClickListener {
            subscribeToValidationResult()
            loginViewModel.onLoginButtonClicked(binding.phoneInputText.text.toString())
        }
        binding.signInButton.setOnClickListener {
            loginViewModel.signInGoogleButtonClicked()
        }
    }

    private fun subscribeToValidationResult() {
        loginViewModel.validationPhoneResult.observe(viewLifecycleOwner) { errorText ->
            if (errorText != null) {
                binding.phoneInputText.error = errorText
                loginViewModel.validationPhoneResult.removeObservers(viewLifecycleOwner)
            }
        }
    }
}