package com.app.homework11

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.homework11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.run {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}