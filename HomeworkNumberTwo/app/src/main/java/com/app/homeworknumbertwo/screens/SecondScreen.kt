package com.app.homeworknumbertwo.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.homeworknumbertwo.repository.Club
import com.app.homeworknumbertwo.R
import com.app.homeworknumbertwo.databinding.FragmentSecondScreenBinding
import com.bumptech.glide.Glide
import com.ranis.homeworknumbertwo.ClubRepository

class SecondScreen : Fragment(R.layout.fragment_second_screen) {

    private var binding: FragmentSecondScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSecondScreenBinding.bind(view)
        val id = arguments?.getInt(ARG_ID)
        val list = ClubRepository.clubs


        var index: Int? = null
        var club: Club? = null

        for (i in list.indices) {
            if (id?.equals(list[i].id) == true) {
                index = i
            }
        }

        index?.let { club = list[it] }

        binding?.run {
            tvTitle.text = club?.name
            tvInfo.text = club?.info
            Glide.with(requireContext())
                .load(club?.url)
                .error(R.drawable.img)
                .into(ivLogo)
        }

        binding?.btnBack?.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,FirstScreen())
                .addToBackStack("ToFirstFragmentFromSecondFragment")
                .commit()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    companion object{
        private const val ARG_ID = "ID"

        fun newInstance(message: Int) = SecondScreen().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, message)
            }
        }
    }


}