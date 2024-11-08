package com.app.homeworknumberone

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.app.homeworknumberone.databinding.ActivityMainBinding
import com.app.homeworknumberone.fragments.FirstFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            binding.run {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, FirstFragment(), "ToFirstFragmentFromMainActivity")
                    .commit()
            }
        }


    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}