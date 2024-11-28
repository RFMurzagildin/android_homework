package com.app.homeworknumbertwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.homeworknumbertwo.databinding.ItemClubCustomHorizontalBinding
import com.app.homeworknumbertwo.databinding.ItemClubCustomVerticalBinding
import com.app.homeworknumbertwo.repository.Club
import com.bumptech.glide.Glide

class CustomClubAdapter(
    private val list: MutableList<Club>,
    private val onClick: (Club) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_FULL_WIDTH = 0
        const val TYPE_HALF_WIDTH = 1
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FULL_WIDTH -> {
                CustomHorizontalClubHolder(
                    binding = ItemClubCustomHorizontalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClick = onClick,
                )
            }
            TYPE_HALF_WIDTH -> {
                CustomVerticalClubHolder(
                    binding = ItemClubCustomVerticalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClick = onClick,
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CustomHorizontalClubHolder -> {
                holder.onBind(list[position])
            }
            is CustomVerticalClubHolder -> {
                holder.onBind(list[position])
            }
        }
    }

    override fun getItemCount():Int = list.size

    override fun getItemViewType(position: Int): Int {
        return if (position % 4 == 0 || position % 4 == 3) {
            TYPE_FULL_WIDTH
        } else {
            TYPE_HALF_WIDTH
        }
    }

    class CustomHorizontalClubHolder(
        private val binding: ItemClubCustomHorizontalBinding,
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

    class CustomVerticalClubHolder(
        private val binding: ItemClubCustomVerticalBinding,
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
}