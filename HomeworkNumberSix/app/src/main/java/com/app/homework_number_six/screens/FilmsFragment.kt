package com.app.homework_number_six.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.homework_number_six.R
import com.app.homework_number_six.databinding.FragmentFilmsBinding
import com.app.homework_number_six.db.entities.FilmsEntity
import com.app.homework_number_six.db.repository.FilmRepository
import com.app.homework_number_six.di.ServiceLocator
import com.app.homework_number_six.utils.AuthPreferences
import com.app.homework_number_six.utils.logout
import com.app.homework_number_six.utils.recycler_view.FilmAdapter
import com.app.homework_number_six.utils.recycler_view.FilmItemTouchHelperCallback
import kotlinx.coroutines.launch

class FilmsFragment : Fragment(R.layout.fragment_films) {

    private var binding: FragmentFilmsBinding? = null
    private var filmRepository: FilmRepository = ServiceLocator.getFilmRepository()
    private var filmAdapter: FilmAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilmsBinding.bind(view)

        val userId = AuthPreferences.getUserId(requireContext()) ?: ""

        binding?.run {
            bnvMain.selectedItemId = R.id.main
            bnvMain.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.main -> {
                        true
                    }
                    R.id.settings -> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, SettingsFragment(), "Settings")
                            .addToBackStack(null)
                            .commit()
                        true
                    }
                    R.id.exit -> {
                        logout(requireContext())
                        true
                    }
                    else -> false
                }
            }
        }

        lifecycleScope.launch {
            val userFilms = filmRepository.getUserFilms(userId)
            setupRecyclerView(userFilms)
        }

        binding?.buttonAddFilm?.setOnClickListener {
            val bottomSheet = AddFilmBottomSheetDialog()
            bottomSheet.show(childFragmentManager, getString(R.string.addfilmbottomsheet))
        }
    }

    private fun setupRecyclerView(films: MutableList<FilmsEntity>?) {
        filmAdapter = films?.let { FilmAdapter(it) }

        binding?.recyclerViewFilms?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = filmAdapter
            val itemTouchHelper = ItemTouchHelper(FilmItemTouchHelperCallback(requireContext(), adapter = filmAdapter!!))
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    fun updateFilmList() {
        val userId = AuthPreferences.getUserId(requireContext()) ?: ""
        lifecycleScope.launch {
            val updatedFilms = filmRepository.getUserFilms(userId) ?: emptyList()
            filmAdapter?.updateData(updatedFilms)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        filmAdapter = null
    }
}