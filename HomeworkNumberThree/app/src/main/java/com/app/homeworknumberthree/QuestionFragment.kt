package com.app.homeworknumberthree

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestionFragment(private val question: Question) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val questionText: TextView = view.findViewById(R.id.questionText)
        val recyclerView: RecyclerView = view.findViewById(R.id.optionsRecyclerView)

        questionText.text = question.questionText
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = OptionsAdapter(requireContext(),question)

        return view
    }
}


