package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.app.alina.R
import com.fit.app.alina.data.User
import com.fit.app.alina.databinding.FragmentMainScreenBinding
import com.fit.app.alina.databinding.FragmentProfileBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.viewModel.MainViewModel

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    val mainViewModel: MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }
    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        mainViewModel.userData.observe(viewLifecycleOwner) { userData ->
            user = userData
            binding.parameters.text =
                "Ваши параметры: \nВес: ${user!!.currentWeight} кг \nЖелаемый вес: ${user!!.desiredWeight} кг \nРост ${user!!.height} см\nПол: ${user!!.gender} \nВозраст: ${user!!.age}"
        }
        return binding.root
    }

}