package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.app.alina.data.dataClasses.Article
import com.fit.app.alina.databinding.FragmentArticlesBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.ui.adapters.ArticlesAdapter

class ArticlesFragment : Fragment() {

    private lateinit var binding: FragmentArticlesBinding
    private val mainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(layoutInflater)
        mainViewModel.articlesData.observe(viewLifecycleOwner) {
            binding.articles.adapter = ArticlesAdapter(it, this)
        }
        mainViewModel.articlesScreenOpened()
        return binding.root
    }

    fun itemClicked(article: Article) {
        mainViewModel.articleChosed(article)
    }
}