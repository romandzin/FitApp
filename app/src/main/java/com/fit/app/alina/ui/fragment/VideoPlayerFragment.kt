package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.app.alina.R
import com.fit.app.alina.databinding.FragmentEnterDataBinding
import com.fit.app.alina.databinding.FragmentVideoPlayerBinding

class VideoPlayerFragment : Fragment() {

    lateinit var binding: FragmentVideoPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

}