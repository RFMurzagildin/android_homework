package com.app.homeworknumbertwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.homeworknumbertwo.databinding.ItemClubGridBinding
import com.app.homeworknumbertwo.holders.GridClubHolder
import com.app.homeworknumbertwo.repository.Club

class GridClubAdapter(
    private val list: MutableList<Club>,
    private val onClick: (Club) -> Unit,
    private val onItemLongClick: (Int) -> Unit,
) : RecyclerView.Adapter<GridClubHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridClubHolder = GridClubHolder(
        binding = ItemClubGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onClick = onClick,
        onItemLongClick = onItemLongClick,
    )

    override fun onBindViewHolder(holder: GridClubHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}