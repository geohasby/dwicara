package com.bangkit.dwicara.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.dwicara.core.domain.User
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

        setUpView()
    }

    private fun setUpView() {
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
            .load(user.photo_url)
            .circleCrop()
            .into(binding.ivAvatar)

        binding.apply {
            tvName.text = user.name
            tvStatus.text = user.status?:"-"
            tvUsername.text = "@geohasby"
        }
    }

    companion object {
        private val user = User(null, "Geohasby Ammar K", "https://cdn.pixabay.com/photo/2016/11/21/14/53/man-1845814__340.jpg",null,null,null)
    }
}