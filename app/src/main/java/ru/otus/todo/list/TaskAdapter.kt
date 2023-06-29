package ru.otus.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.todo.databinding.ItemRecyclerViewBinding
import ru.otus.todo.main.Task

class TaskAdapter : RecyclerView.Adapter<ItemViewHolder> () {
    private val list = mutableListOf<Task>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitData(data: MutableList<Task>) {
        list.clear()
        list.addAll(data)
    }

    fun addData(data: MutableList<Task>) {
        list.addAll(data)
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
    }

    fun getTask(position: Int): Task {
        return list[position]
    }

}