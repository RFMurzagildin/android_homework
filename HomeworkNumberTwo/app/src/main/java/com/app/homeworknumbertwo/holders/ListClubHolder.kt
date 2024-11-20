package com.app.homeworknumbertwo.holders

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.homeworknumbertwo.repository.Club
import com.app.homeworknumbertwo.databinding.ItemClubListBinding
import com.bumptech.glide.Glide

class ListClubHolder(
    private val binding: ItemClubListBinding,
    private val onClick: (Club) -> Unit,
) : ViewHolder(binding.root) {

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