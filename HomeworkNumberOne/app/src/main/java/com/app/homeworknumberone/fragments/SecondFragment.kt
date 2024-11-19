package com.app.homeworknumberone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.app.homeworknumberone.R
import com.app.homeworknumberone.buttomsheets.BottomSheetFragment
import com.app.homeworknumberone.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    private var binding: FragmentSecondBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
        arguments?.getString(ARG_NAME)?.also {
            binding?.tvText?.text = it
        }


        binding?.run {
            buttonToThirdScreen.setOnClickListener{
                val message: String = tvText.text.toString()
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    .replace(R.id.fragment_container,ThirdFragment.newInstance(message))
                    .addToBackStack("ToThirdFragmentFromFirstFragment")
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object{

        private const val ARG_NAME = "MESSAGE"

        fun newInstance(message: String) = SecondFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, message)
            }
        }
    }

}