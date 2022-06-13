package com.bangkit.dwicara.friendsprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.dwicara.databinding.ActivityFriendsProfileBinding
import com.bumptech.glide.Glide

class FriendsProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendsProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()
        }

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2017/04/01/21/06/portrait-2194457__340.jpg")
            .circleCrop()
            .into(binding.avatar)
    }
}