package com.app.homework_number_six.utils.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.app.homework_number_six.db.entities.FilmsEntity

class FilmDiffCallback(
    private val oldList: List<FilmsEntity>,
    private val newList: List<FilmsEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}