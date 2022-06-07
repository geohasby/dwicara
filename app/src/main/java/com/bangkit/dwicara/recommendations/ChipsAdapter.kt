package com.bangkit.dwicara.recommendations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.dwicara.core.domain.Interest
import com.bangkit.dwicara.databinding.ChipsItemBinding

class ChipsAdapter(
    private val listItems: List<Interest>,
    private val clickCallback: (Interest) -> Unit = { }
) : RecyclerView.Adapter<ChipsAdapter.ChipsViewHolder>() {
    class ChipsViewHolder(val binding: ChipsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipsViewHolder {
        val binding = ChipsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChipsViewHolder, position: Int) {
        val (name) = listItems[position]

        holder.binding.chips.text = name

        holder.itemView.setOnClickListener {
            clickCallback(listItems[position])
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}