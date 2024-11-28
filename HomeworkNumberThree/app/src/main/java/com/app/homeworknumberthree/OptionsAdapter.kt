package com.app.homeworknumberthree

import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class OptionsAdapter(
    private val context: Context,
    private val question: Question
) : RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = question.options[position]
        holder.optionText.text = option
        holder.radioButton.isChecked = position == question.markedIndex
        holder.mcvVariant.setBackgroundResource(if(position == question.markedIndex) R.drawable.shape_rounded_containers_correct else R.drawable.shape_rounded_containers)
        holder.radioButton.setOnClickListener{
            question.markedIndex = position
            notifyDataSetChanged()
        }
        holder.mcvVariant.setOnClickListener{
            question.markedIndex = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = question.options.size

    class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val optionText: TextView = view.findViewById(R.id.optionText)
        val radioButton: RadioButton = view.findViewById(R.id.radioButton)
        val mcvVariant: MaterialCardView = itemView.findViewById(R.id.mcvVariant)
    }
}
