package com.app.HomeworkNumberFour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ColorAdapter(
    private val colors: List<Int>,
    private val onColorClick: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    class ColorViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.view.findViewById<View>(R.id.colorView).setBackgroundColor(color)

        holder.view.setOnClickListener {
            onColorClick(color)
        }
    }

    override fun getItemCount(): Int = colors.size
}
