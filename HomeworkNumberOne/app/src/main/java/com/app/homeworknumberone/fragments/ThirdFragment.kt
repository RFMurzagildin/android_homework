package com.app.homeworknumberone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.homeworknumberone.R
import com.app.homeworknumberone.databinding.FragmentThirdBinding
import com.app.homeworknumberone.fragments.SecondFragment.Companion

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var binding: FragmentThirdBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdBinding.bind(view)
        arguments?.getString(ARG_NAME)?.also {
            binding?.tvText?.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    companion object{

        private const val ARG_NAME = "MESSAGE"

        fun newInstance(message: String) = ThirdFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, message)
            }
        }
    }

}