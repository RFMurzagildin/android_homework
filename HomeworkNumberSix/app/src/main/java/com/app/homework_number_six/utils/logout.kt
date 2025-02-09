package com.app.homework_number_six.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.app.homework_number_six.R
import com.app.homework_number_six.screens.LoginFragment

fun logout(context: Context) {

    AuthPreferences.clearUserId(context)

    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
    fragmentManager.beginTransaction()
        .replace(R.id.fragment_container, LoginFragment(), context.getString(R.string.login))
        .commit()
}