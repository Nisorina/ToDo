package ru.otus.todo.list

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ru.otus.todo.databinding.ItemRecyclerViewBinding
import ru.otus.todo.main.Task

class ItemViewHolder(private val binding: ItemRecyclerViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task) {
        binding.content.text = task.content
    }

    fun getSwipedView(): LinearLayout? {
        return binding.mainLayout
    }
}