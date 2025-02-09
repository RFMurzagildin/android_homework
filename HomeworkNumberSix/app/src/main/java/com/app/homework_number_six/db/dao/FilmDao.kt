package com.app.homework_number_six.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.homework_number_six.db.entities.FilmsEntity

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmInDb(film: FilmsEntity)

    @Query("SElECT * FROM films WHERE user_id = :userId")
    suspend fun getUserFilms(userId: String): MutableList<FilmsEntity>?

    @Query("DELETE FROM films WHERE id = :filmId")
    suspend fun deleteFilmById(filmId: String)
}