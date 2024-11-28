package com.app.homeworknumbertwo.screens

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.homeworknumberone.buttomsheets.BottomSheetFragment
import com.app.homeworknumbertwo.R
import com.app.homeworknumbertwo.adapters.CustomClubAdapter
import com.app.homeworknumbertwo.adapters.GridClubAdapter
import com.app.homeworknumbertwo.databinding.FragmentFirstScreenBinding
import com.app.homeworknumbertwo.adapters.ListClubAdapter
import com.app.homeworknumbertwo.repository.Club
import com.ranis.homeworknumbertwo.ClubRepository
import kotlin.math.min

class FirstScreen : Fragment(R.layout.fragment_first_screen) {
    private var binding: FragmentFirstScreenBinding? = null

    private var listAdapter: ListClubAdapter? = null
    private var gridAdapter: GridClubAdapter? = null
    private var customAdapter: CustomClubAdapter? = null
    private var currentList: MutableList<Club> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstScreenBinding.bind(view)
        currentList = ClubRepository.currentClubs

        initListAdapter(currentList)
        initGridAdapter(currentList)
        initCustomAdapter(currentList)

        binding?.run {
            list.setOnClickListener {
                rvListFootballClubs.isVisible = true
                rvGridFootballClubs.isVisible = false
                rvCustomFootballClubs.isVisible = false
            }
            grid.setOnClickListener {
                rvListFootballClubs.isVisible = false
                rvGridFootballClubs.isVisible = true
                rvCustomFootballClubs.isVisible = false
            }
            custom.setOnClickListener {
                rvListFootballClubs.isVisible = false
                rvGridFootballClubs.isVisible = false
                rvCustomFootballClubs.isVisible = true
            }
        }

        binding?.fabBottomSheet?.setOnClickListener {
            val dialog = BottomSheetFragment()
            dialog.adding = { mutableList ->
                for (club in mutableList) {
                    currentList.add((0..currentList.size).random(), club)
                }
                initListAdapter(currentList)
                initGridAdapter(currentList)
                initCustomAdapter(currentList)
            }
            dialog.deletion = { numbers ->
                val numberDeleted = min(numbers, currentList.size)
                if (currentList.size > 0) {
                    for (i in 1..numberDeleted) {
                        currentList.removeAt((0..<currentList.size).random())
                        initListAdapter(currentList)
                        initGridAdapter(currentList)
                        initCustomAdapter(currentList)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "It is not possible to delete items!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            dialog.show(childFragmentManager, "ToButtonToBottomSheetFromFirstFragment")
        }

    }

    private fun initListAdapter(mutableList: MutableList<Club>) {
        listAdapter = ListClubAdapter(
            list = mutableList,
            onClick = { club ->
                ClubRepository.currentClubs = currentList
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SecondScreen.newInstance(club.id))
                    .addToBackStack("ToSecondFragmentFromFirstFragment")
                    .commit()
            }
        )
        binding?.run {
            rvListFootballClubs.adapter = listAdapter
            rvListFootballClubs.layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    listAdapter?.removeItem(position)
                }

                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    if (dX < 0) {
                        // Определяем ширину элемента
                        val itemWidth = viewHolder.itemView.width
                        // Устанавливаем границу для свайпа
                        val swipeThreshold = itemWidth * 0.66f
                    }

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            })

        itemTouchHelper.attachToRecyclerView(binding?.rvListFootballClubs)
    }

    @SuppressLint("ResourceType")
    private fun initGridAdapter(mutableList: MutableList<Club>) {
        val recyclerView: RecyclerView? = binding?.rvGridFootballClubs
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 3)

        gridAdapter = GridClubAdapter(currentList, onClick = { club ->
            ClubRepository.currentClubs = currentList

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SecondScreen.newInstance(club.id))
                .addToBackStack("ToSecondFragmentFromFirstFragment")
                .commit()
        }, onItemLongClick = {
                position -> onItemLongClick(position)
        }
        )

        binding?.run {
            rvGridFootballClubs.adapter = gridAdapter
        }
    }

    private fun initCustomAdapter(mutableList: MutableList<Club>){
        customAdapter = CustomClubAdapter(
            list = mutableList,
            onClick = { club ->
                ClubRepository.currentClubs = currentList
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SecondScreen.newInstance(club.id),)
                    .addToBackStack("ToSecondFragmentFromFirstFragment")
                    .commit()
            }
        )
        binding?.rvCustomFootballClubs?.adapter = customAdapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position % 4) {
                    0, 3 -> 2 // Первый и четвертый элементы занимают всю ширину
                    else -> 1 // Второй и третий элементы занимают половину ширины
                }
            }
        }
        binding?.rvCustomFootballClubs?.layoutManager = gridLayoutManager

    }

    private fun onItemLongClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Подтверждение удаления")
        builder.setMessage("Вы уверены, что хотите удалить этот элемент?")
        builder.setPositiveButton("Да") { dialog, which ->
            gridAdapter?.removeItem(position)
        }
        builder.setNegativeButton("Нет") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
        private const val ARG_ID = "ID"

        fun newInstance(message: Int) = FirstScreen().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, message)
            }
        }
    }
}



