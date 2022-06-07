package com.bangkit.dwicara.recommendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.dwicara.core.domain.User
import com.bangkit.dwicara.databinding.ListItemBinding
import com.bumptech.glide.Glide

class ListItemAdapter(
    private val listItem: List<User>,
    private val clickCallback: (User) -> Unit = { }
): RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder>() {
    class ListItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val (name, photo_url, status, native, learn) = listItem[position]

        holder.binding.apply {
            tvName.text = name
            tvStatus.text = status
        }

        Glide.with(holder.itemView)
            .load(photo_url)
            .circleCrop()
            .into(holder.binding.ivAvatar)

        Glide.with(holder.itemView)
            .load("https://cdn.pixabay.com/photo/2012/04/10/23/01/indonesia-26817__480.png")
            .centerCrop()
            .into(holder.binding.ivNative)

        Glide.with(holder.itemView)
            .load("https://cdn.pixabay.com/photo/2012/04/10/16/14/union-jack-26119__480.png")
            .centerCrop()
            .into(holder.binding.ivLearn)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}