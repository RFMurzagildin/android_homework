package com.app.homework_number_six.utils.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.homework_number_six.databinding.ItemFilmBinding
import com.app.homework_number_six.db.entities.FilmsEntity

class FilmAdapter(var films: MutableList<FilmsEntity>) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int = films.size

    fun updateData(newFilms: List<FilmsEntity>) {
        val diffCallback = FilmDiffCallback(films, newFilms.toMutableList())
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        films.clear()
        films.addAll(newFilms)
        diffResult.dispatchUpdatesTo(this)
    }
}
