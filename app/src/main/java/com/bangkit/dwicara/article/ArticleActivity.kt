package com.bangkit.dwicara.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.dwicara.databinding.ActivityArticleBinding
import com.bumptech.glide.Glide

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2018/05/08/13/56/globe-3383088__340.jpg")
            .fitCenter()
            .into(binding.ivImage)
    }
}