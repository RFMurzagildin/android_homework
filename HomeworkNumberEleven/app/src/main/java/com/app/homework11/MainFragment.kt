package com.app.homework11

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.homework11.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding:FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding?.pieChart?.setData(
            listOf(
                1 to 46,
                2 to 18,
                3 to 9,
                4 to 7,
                5 to 7,
                6 to 5,
                7 to 2,
                8 to 2,
                9 to 2,
                10 to 2
            ),
            with(requireContext()){
                listOf(
                    getColor(R.color.color1),
                    getColor(R.color.color2),
                    getColor(R.color.color3),
                    getColor(R.color.color4),
                    getColor(R.color.color5),
                    getColor(R.color.color6),
                    getColor(R.color.color7),
                    getColor(R.color.color8),
                    getColor(R.color.color9),
                    getColor(R.color.color10),
                )
            }

        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}