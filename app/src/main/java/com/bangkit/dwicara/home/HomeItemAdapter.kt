package com.bangkit.dwicara.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.ItemListHomeBinding
import com.bumptech.glide.Glide

class HomeItemAdapter(
    private val itemList: List<User>,
    private val clickCallback:(User) -> Unit ={ }
): RecyclerView.Adapter<HomeItemAdapter.HomeItemViewHolder>() {
    class HomeItemViewHolder(val binding: ItemListHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = ItemListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val (name, photo_url) = itemList[position]
        holder.binding.tvName.text = name

        Glide.with(holder.itemView)
            .load(photo_url)
            .circleCrop()
            .into(holder.binding.ivAvatar)

        holder.itemView.setOnClickListener {
            clickCallback(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}