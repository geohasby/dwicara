package com.bangkit.dwicara.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.dwicara.databinding.ActivitySearchBinding
import com.bangkit.dwicara.friendsprofile.FriendsProfileActivity
import com.bangkit.dwicara.recommendations.RecommendationsActivity
import com.bumptech.glide.Glide

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnViewProfile.setOnClickListener{
            startActivity(Intent(this, FriendsProfileActivity::class.java))
        }

        binding.apply {
            ivEmptySearch.visibility = View.GONE
            tvEmptySearch.visibility = View.GONE

            ivAvatar.visibility = View.VISIBLE
            tvUsername.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            tvStatus.visibility = View.VISIBLE
            btnChat.visibility = View.VISIBLE
            btnViewProfile.visibility = View.VISIBLE
        }

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg")
            .circleCrop()
            .into(binding.ivAvatar)
    }
}