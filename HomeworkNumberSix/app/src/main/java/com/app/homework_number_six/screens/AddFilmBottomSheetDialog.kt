package com.app.homework_number_six.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.app.homework_number_six.R
import com.app.homework_number_six.databinding.BottomSheetAddFilmBinding
import com.app.homework_number_six.db.entities.FilmsEntity
import com.app.homework_number_six.db.repository.FilmRepository
import com.app.homework_number_six.di.ServiceLocator
import com.app.homework_number_six.utils.AuthPreferences
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import java.util.UUID

class AddFilmBottomSheetDialog : BottomSheetDialogFragment(R.layout.bottom_sheet_add_film) {

    private var filmRepository: FilmRepository = ServiceLocator.getFilmRepository()
    private var binding: BottomSheetAddFilmBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetAddFilmBinding.bind(view)
        val userId = AuthPreferences.getUserId(requireContext()) ?: ""

        binding?.run {
            buttonAdd.setOnClickListener {
                val filmName = editTextFilmName.text.toString().trim()
                val releaseDate = editTextReleaseDate.text.toString().trim()
                val ratingStr = editTextRating.text.toString().trim()
                val descriptor = editTextDescriptor.text.toString().trim()

                if (filmName.isEmpty() || releaseDate.isEmpty() || ratingStr.isEmpty() || descriptor.isEmpty()) {
                    Toast.makeText(context,
                        getString(R.string.please_fill_in_all_required_fields), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val rating = ratingStr.toFloatOrNull() ?: 0f

                if (rating !in 0f..10f) {
                    Toast.makeText(context,
                        getString(R.string.the_rating_must_be_between_0_and_10), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val newFilm = FilmsEntity(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    filmName = filmName,
                    releaseDate = releaseDate,
                    rating = rating,
                    descriptor = descriptor
                )

                lifecycleScope.launch {
                    filmRepository.saveFilm(newFilm)
                    dismiss()
                    (parentFragment as? FilmsFragment)?.updateFilmList()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}