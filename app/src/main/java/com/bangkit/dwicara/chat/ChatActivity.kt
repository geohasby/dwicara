package com.bangkit.dwicara.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.ActivityChatBinding
import com.bangkit.dwicara.databinding.ChatUserListBinding
import com.bangkit.dwicara.mychat.MyChatFragment
import com.bumptech.glide.Glide

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<User>(MyChatFragment.EXTRA_USER) as User

        setUpView()
    }

    private fun setUpView() {
        binding.tvName.text = user.name
        binding.btnBack.setOnClickListener {
            finish()
        }
        Glide.with(this)
            .load(user.photo_url)
            .circleCrop()
            .into(binding.ivAvatar)
    }
}