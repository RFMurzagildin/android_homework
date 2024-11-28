package com.app.homeworknumberthree

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.app.homeworknumberthree.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var questionsAdapter: QuestionsPagerAdapter
    private lateinit var lastButton: Button
    private lateinit var nextButton: Button
    private lateinit var counterText: TextView

    private val questions = QuestionRepository.questions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        lastButton = findViewById(R.id.lastButton)
        nextButton = findViewById(R.id.nextButton)
        counterText = findViewById(R.id.counterText)

        questionsAdapter = QuestionsPagerAdapter(
            this,
            questions,
            manager = supportFragmentManager,
            lifecycle = this.lifecycle,
        )

        viewPager.adapter = questionsAdapter

        counterText.text = "1/${questions.size}"

        nextButton.setOnClickListener {
            if (viewPager.currentItem < questions.size - 1) {
                viewPager.currentItem += 1
            } else {
                Snackbar.make(it, getString(R.string.results_are_saved), Snackbar.LENGTH_SHORT).show()
            }
        }

        lastButton.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem -= 1
            }
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                counterText.text = "${position + 1}/${questions.size}"

                nextButton.text = if (position == questions.size - 1) getString(R.string.end) else getString(R.string.next)
                nextButton.setBackgroundColor(Color.BLUE)

                lastButton.setBackgroundColor(if (position == 0) Color.LTGRAY else Color.BLUE)
            }
        })

    }
}