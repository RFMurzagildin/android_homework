package com.app.homework_number_six.db.repository

import com.app.homework_number_six.db.dao.FilmDao
import com.app.homework_number_six.db.dao.UserDao
import com.app.homework_number_six.db.entities.FilmsEntity
import com.app.homework_number_six.db.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmRepository(private val filmDao: FilmDao) {

    suspend fun saveFilm(film: FilmsEntity) {
        withContext(Dispatchers.IO) {
            filmDao.saveFilmInDb(film)
        }
    }

    suspend fun getUserFilms(userId: String): MutableList<FilmsEntity>? {
        return withContext(Dispatchers.IO) {
            filmDao.getUserFilms(userId)
        }
    }

    suspend fun deleteFilm(filmId: String) {
        withContext(Dispatchers.IO) {
            filmDao.deleteFilmById(filmId)
        }
    }
}