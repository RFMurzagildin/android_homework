package com.app.homeworknumberone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.app.homeworknumberone.R
import com.app.homeworknumberone.buttomsheets.BottomSheetFragment
import com.app.homeworknumberone.databinding.FragmentFirstBinding
import com.app.homeworknumberone.fragments.SecondFragment.Companion

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        binding?.buttonToSecondScreen?.setOnClickListener{

            val message: String = binding?.editText?.text.toString()

            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(R.id.fragment_container,SecondFragment.newInstance(message))
                .addToBackStack("ToSecondFragmentFromFirstFragment")
                .commit()
        }

        binding?.buttonToThirdScreen?.setOnClickListener{

            val message: String = binding?.editText?.text.toString()

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,SecondFragment.newInstance(message))
                .addToBackStack("ToThirdFragmentFromFirstFragment")
                .commit()
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

        binding?.buttonToBottomSheet?.setOnClickListener{
            val dialog = BottomSheetFragment()
            dialog.sendingMessage = { text ->
                binding?.editText?.setText(text)
            }
            dialog.show(childFragmentManager, "ToButtonToBottomSheetFromFirstFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    companion object{

        private const val ARG_NAME = "MESSAGE"

        fun newInstance(message: String) = FirstFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, message)
            }
        }
    }


}