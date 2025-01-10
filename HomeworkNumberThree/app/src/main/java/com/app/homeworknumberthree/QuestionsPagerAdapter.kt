package com.app.homeworknumberthree

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuestionsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val questions: List<Question>,
    manager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment(questions[position])
    }

    override fun getItemCount(): Int = questions.size
}