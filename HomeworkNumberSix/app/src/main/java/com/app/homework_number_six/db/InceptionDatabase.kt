package com.app.homework_number_six.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.homework_number_six.db.dao.FilmDao
import com.app.homework_number_six.db.dao.UserDao
import com.app.homework_number_six.db.entities.FilmsEntity
import com.app.homework_number_six.db.entities.UserEntity

@Database(
    entities = [UserEntity::class, FilmsEntity::class],
    version = 4
)
abstract class InceptionDatabase : RoomDatabase(){
    abstract val userDao: UserDao
    abstract val filmDao: FilmDao

    companion object{
        const val DB_LOG_KEY = "InceptionDB"
    }
}