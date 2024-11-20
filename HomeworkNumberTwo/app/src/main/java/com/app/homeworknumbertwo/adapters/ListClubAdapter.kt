package com.app.homeworknumbertwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.homeworknumbertwo.repository.Club
import com.app.homeworknumbertwo.holders.ListClubHolder
import com.app.homeworknumbertwo.databinding.ItemClubListBinding

class ListClubAdapter(
    private val list: MutableList<Club>,
    private val onClick: (Club) -> Unit,
) : RecyclerView.Adapter<ListClubHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListClubHolder = ListClubHolder(
        binding = ItemClubListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onClick = onClick
    )

    override fun onBindViewHolder(holder: ListClubHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}