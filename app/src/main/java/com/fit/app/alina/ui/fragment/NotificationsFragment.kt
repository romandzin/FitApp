package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.app.alina.R
import com.fit.app.alina.data.User
import com.fit.app.alina.databinding.FragmentNotificationsBinding
import com.fit.app.alina.databinding.FragmentProfileBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.viewModel.MainViewModel

class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private val mainViewModel: MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }
    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }
}