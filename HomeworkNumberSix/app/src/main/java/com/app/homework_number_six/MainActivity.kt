package com.app.homework_number_six

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.homework_number_six.di.ServiceLocator
import com.app.homework_number_six.screens.FilmsFragment
import com.app.homework_number_six.screens.LoginFragment
import com.app.homework_number_six.utils.AuthPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AuthPreferences.getUserId(this) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FilmsFragment(), getString(R.string.films))
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment(), getString(R.string.login))
                .commit()
        }
    }
}