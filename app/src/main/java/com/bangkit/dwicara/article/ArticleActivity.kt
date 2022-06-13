package com.bangkit.dwicara.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.dwicara.core.domain.Article
import com.bangkit.dwicara.databinding.ActivityArticleBinding
import com.bangkit.dwicara.home.HomeFragment
import com.bumptech.glide.Glide

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding

    private lateinit var dailyArticle: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dailyArticle = intent.getParcelableExtra<Article>(HomeFragment.EXTRA_ARTICLE) as Article

        setUpView()
    }

    private fun setUpView() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvTitle.text = dailyArticle.title

        binding.tvDate.text = dailyArticle.date

        binding.tvSource.text = dailyArticle.source

        binding.tvContent.text = dailyArticle.content

        Glide.with(this)
            .load(dailyArticle.img_url)
            .fitCenter()
            .into(binding.ivImage)
    }
}