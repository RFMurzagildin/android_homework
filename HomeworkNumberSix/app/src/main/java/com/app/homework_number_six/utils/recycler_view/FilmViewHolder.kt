package com.app.homework_number_six.utils.recycler_view

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.app.homework_number_six.databinding.ItemFilmBinding
import com.app.homework_number_six.db.entities.FilmsEntity

class FilmViewHolder(private val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(film: FilmsEntity) {
        with(binding){
            textViewFilmName.text = film.filmName
            textViewReleaseDate.text = film.releaseDate
            textViewRating.text = film.rating.toString()
            textViewDescriptor.text = film.descriptor
        }
    }
}