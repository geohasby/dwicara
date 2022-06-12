package com.bangkit.dwicara.mychat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.ChatUserListBinding
import com.bumptech.glide.Glide

class ChatUserAdapter(
    private val itemList: List<User>,
    private val clickCallback: (User) -> Unit = { }
): RecyclerView.Adapter<ChatUserAdapter.ChatUserViewHolder>() {
    class ChatUserViewHolder(val binding: ChatUserListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserViewHolder {
        val binding = ChatUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatUserViewHolder, position: Int) {
        val (_, name, photo_url) = itemList[position]

        holder.binding.apply {
            tvName.text = name
            tvMessage.text = "Hello! Good Morning! How are you going?"
            tvCount.text = "1"
            tvTime.text = "12:41 AM"
        }

        Glide.with(holder.itemView)
            .load(photo_url)
            .circleCrop()
            .into(holder.binding.ivAvatar)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}