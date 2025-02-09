package com.app.homework_number_six.di

import android.content.Context
import androidx.room.Room
import com.app.homework_number_six.db.InceptionDatabase
import com.app.homework_number_six.db.migrations.Migration_1_2
import com.app.homework_number_six.db.migrations.Migration_2_3
import com.app.homework_number_six.db.migrations.Migration_3_4
import com.app.homework_number_six.db.repository.FilmRepository
import com.app.homework_number_six.db.repository.UserRepository

object ServiceLocator {

    private const val DATABASE_NAME = "InceptionDB"

    private var dbInstance: InceptionDatabase? = null

    private var userRepository: UserRepository? = null
    private var filmRepository: FilmRepository? = null


    private fun initDatabase(ctx: Context){
        dbInstance = Room.databaseBuilder(ctx, InceptionDatabase::class.java, DATABASE_NAME)
            .addMigrations(
                Migration_1_2(),
                Migration_2_3(),
                Migration_3_4()
            )
            .build()
    }

    fun initDataLayerDependencies(ctx: Context){
        if(dbInstance == null){
            initDatabase(ctx)
            dbInstance?.let{
                userRepository = UserRepository(it.userDao)
                filmRepository = FilmRepository(it.filmDao)
            }
        }
    }

    fun getUserRepository(): UserRepository = userRepository
        ?: throw IllegalStateException()

    fun getFilmRepository(): FilmRepository = filmRepository
        ?: throw IllegalStateException()
}