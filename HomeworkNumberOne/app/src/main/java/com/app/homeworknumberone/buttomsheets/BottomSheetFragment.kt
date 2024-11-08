package com.app.homeworknumberone.buttomsheets

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.app.homeworknumberone.MainActivity
import com.app.homeworknumberone.R
import com.app.homeworknumberone.databinding.DialogBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment(R.layout.dialog_bottom_sheet){

    private var binding: DialogBottomSheetBinding? = null
    var sendingMessage: ((String) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogBottomSheetBinding.bind(view)

        binding?.buttonSendMessage?.setOnClickListener {
            if(binding?.editTextInBottomSheet?.text.toString().isNotEmpty()){
                binding?.buttonSendMessage?.isEnabled = true
                sendingMessage?.invoke(binding?.editTextInBottomSheet?.text.toString())
                dismiss()
            }else{
                Toast.makeText(context, "Enter the text!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
    }

}