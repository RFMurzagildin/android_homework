package com.app.homeworknumbertwo.holders

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.homeworknumbertwo.repository.Club
import com.app.homeworknumbertwo.databinding.ItemClubGridBinding
import com.bumptech.glide.Glide

class GridClubHolder(
    private val binding: ItemClubGridBinding,
    private val onClick: (Club) -> Unit,
    private val onItemLongClick: (Int) -> Unit,
) : ViewHolder(binding.root) {
    init {
        binding.root.setOnLongClickListener {
            onItemLongClick(adapterPosition) // Вызов функции обратного вызова
            true
        }
    }
    fun onBind(club: Club) {
        binding.run {
            tvNameFootballClub.text = club.name
            Glide.with(itemView.context)
                .load(club.url)
                .into(ivImage)
            root.setOnClickListener {
                onClick(club)
            }
        }
    }
}