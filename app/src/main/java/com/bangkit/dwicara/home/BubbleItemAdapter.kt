package com.bangkit.dwicara.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.BubbleItemBinding
import com.bumptech.glide.Glide

class BubbleItemAdapter(
    private val itemList: List<User>,
    private val clickCallback:(User) -> Unit ={ }
): RecyclerView.Adapter<BubbleItemAdapter.BubbleItemViewHolder>() {
    class BubbleItemViewHolder(val binding: BubbleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BubbleItemViewHolder {
        val binding = BubbleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BubbleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BubbleItemViewHolder, position: Int) {
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