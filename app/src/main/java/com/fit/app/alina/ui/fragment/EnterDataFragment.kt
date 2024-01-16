package com.fit.app.alina.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.fit.app.alina.R
import com.fit.app.alina.databinding.FragmentEnterDataBinding
import com.fit.app.alina.databinding.FragmentLoginBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.viewModel.LoginViewModel
import kotlin.math.log

class EnterDataFragment : Fragment() {

    lateinit var binding: FragmentEnterDataBinding
    private lateinit var loginViewModel: LoginViewModel
    private var currentScreen = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterDataBinding.inflate(inflater, container, false)
        initViewModel()
        showNameScreen()
        return binding.root
    }

    private fun initViewModel() {
        val parentActivity = activity as MainActivity
        loginViewModel = parentActivity.loginViewModel
    }

    private fun moveToNextScreen() {
        currentScreen += 1
        update()
    }

    private fun update() {
        when (currentScreen) {
            0 -> showNameScreen()
            1 -> showGenderScreen()
            2 -> showDataScreen()
        }
    }

    private fun showNameScreen() {
        binding.title.text = "Введите Ваше имя"
        binding.name.isVisible = true
        binding.age.isVisible = false
        binding.currentWeight.isVisible = false
        binding.height.isVisible = false
        binding.desireWeight.isVisible = false
        binding.maleButton.isVisible = false
        binding.femaleButton.isVisible = false
        binding.nextButton.setOnClickListener {
            loginViewModel.onNextNameButtonClicked(binding.name.text.toString())
            moveToNextScreen()
        }
    }

    private fun showGenderScreen() {
        binding.title.text = "Привет, ${loginViewModel.currentUser?.name}"
        binding.name.isVisible = false
        binding.age.isVisible = false
        binding.currentWeight.isVisible = false
        binding.height.isVisible = false
        binding.desireWeight.isVisible = false
        binding.maleButton.isVisible = true
        binding.femaleButton.isVisible = true
        binding.nextButton.isVisible = false
        binding.maleButton.setOnClickListener {
            loginViewModel.onNextGenderButtonClicked("male")
            moveToNextScreen()
        }
        binding.femaleButton.setOnClickListener {
            loginViewModel.onNextGenderButtonClicked("female")
            moveToNextScreen()
        }
    }

    private fun showDataScreen() {
        binding.name.isVisible = false
        binding.age.isVisible = true
        binding.currentWeight.isVisible = true
        binding.height.isVisible = true
        binding.desireWeight.isVisible = true
        binding.maleButton.isVisible = false
        binding.femaleButton.isVisible = false
        binding.nextButton.isVisible = true
        binding.nextButton.setOnClickListener {
            loginViewModel.validationAgeResult.observe(viewLifecycleOwner) {errorText ->
                binding.age.error = errorText
            }
            loginViewModel.validationHeightResult.observe(viewLifecycleOwner) {errorText ->
                binding.height.error = errorText
            }
            loginViewModel.validationDesireWeightResult.observe(viewLifecycleOwner) {errorText ->
                binding.desireWeight.error = errorText
            }
            loginViewModel.validationCurrentWeightResult.observe(viewLifecycleOwner) {errorText ->
                binding.currentWeight.error = errorText
            }
            loginViewModel.onNextDataButtonClicked(
                binding.age.text.toString(),
                binding.height.text.toString(),
                binding.currentWeight.text.toString(),
                binding.desireWeight.text.toString()
            )
        }
    }
}