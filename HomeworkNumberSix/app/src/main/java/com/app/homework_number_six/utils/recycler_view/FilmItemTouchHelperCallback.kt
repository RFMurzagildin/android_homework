package com.app.homework_number_six.utils.recycler_view

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.homework_number_six.db.repository.FilmRepository
import com.app.homework_number_six.di.ServiceLocator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmItemTouchHelperCallback(
    val context: Context,
    val adapter: FilmAdapter
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val filmRepository = ServiceLocator.getFilmRepository()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val filmToDelete = adapter.films[position]

        CoroutineScope(Dispatchers.IO).launch {
            filmRepository.deleteFilm(filmToDelete.id)

            adapter.films.removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, adapter.itemCount)
        }
    }
}