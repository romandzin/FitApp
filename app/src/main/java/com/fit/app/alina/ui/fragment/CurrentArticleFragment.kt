package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.app.alina.R
import com.fit.app.alina.databinding.FragmentCurrentArticleBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.squareup.picasso.Picasso

class CurrentArticleFragment : Fragment() {

    lateinit var binding: FragmentCurrentArticleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentArticleBinding.inflate(layoutInflater)
        (activity as MainActivity).mainViewModel.chosenArticleData.observe(viewLifecycleOwner) {article ->
            binding.articleTitle.text = article.articleTitle
            binding.mainText.text = article.text
            Picasso.with(context)
                .load(article.photo)
                .into(binding.articlePhoto)
        }
        return binding.root
    }
}