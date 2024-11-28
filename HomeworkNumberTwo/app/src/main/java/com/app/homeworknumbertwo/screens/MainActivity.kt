package com.app.homeworknumbertwo.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.homeworknumbertwo.R
import com.app.homeworknumbertwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            binding.run {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, FirstScreen())
                    .commit()
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}