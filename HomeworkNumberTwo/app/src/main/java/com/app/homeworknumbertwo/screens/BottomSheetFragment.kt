package com.app.homeworknumberone.buttomsheets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.homeworknumbertwo.R
import com.app.homeworknumbertwo.databinding.DialogBottomSheetBinding
import com.app.homeworknumbertwo.repository.Club
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ranis.homeworknumbertwo.ClubRepository

class BottomSheetFragment : BottomSheetDialogFragment(R.layout.dialog_bottom_sheet){

    private var binding: DialogBottomSheetBinding? = null
    var adding: ((List<Club>) -> Unit)? = null
    var deletion: ((Int) -> Unit)? = null
    private var clubList: List<Club> = ClubRepository.clubs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogBottomSheetBinding.bind(view)

        binding?.btn1?.setOnClickListener{
            val numbers = binding?.editTextInBottomSheet?.text.toString()
            if(numbers.isEmpty()){
                Toast.makeText(context, "Enter a count clubs!", Toast.LENGTH_SHORT).show()
            }else{
                val sentClubs: MutableList<Club> = mutableListOf()
                for (i in 1..numbers.toInt()) {
                    sentClubs.add(clubList.random())
                }
                adding?.invoke(sentClubs)
                dismiss()
            }

        }

        binding?.btn2?.setOnClickListener{
            val numbers = binding?.editTextInBottomSheet?.text.toString()
            if(numbers.isEmpty()){
                Toast.makeText(context, "Enter a count clubs!", Toast.LENGTH_SHORT).show()
            }else{
                deletion?.invoke(numbers.toInt())
                dismiss()
            }

        }

        binding?.btn3?.setOnClickListener{
            val sentClubs: MutableList<Club> = mutableListOf()
            sentClubs.add(clubList.random())
            adding?.invoke(sentClubs)
            dismiss()
        }

        binding?.btn4?.setOnClickListener{
            deletion?.invoke(1)
            dismiss()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
    }

}