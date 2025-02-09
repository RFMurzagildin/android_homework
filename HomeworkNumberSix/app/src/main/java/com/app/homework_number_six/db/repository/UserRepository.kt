package com.app.homework_number_six.db.repository

import com.app.homework_number_six.db.dao.UserDao
import com.app.homework_number_six.db.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun getUserByEmail(email: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email)
        }
    }

    suspend fun getUserByNickname(nickname: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByNickname(nickname)
        }
    }

    suspend fun saveUser(user: UserEntity) {
        return withContext(Dispatchers.IO) {
            userDao.saveUserData(user = user)
        }
    }

    suspend fun deleteUserById(userId: String) {
        withContext(Dispatchers.IO) {
            userDao.deleteUserById(userId)
        }
    }

    suspend fun getUserById(userId: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }
}