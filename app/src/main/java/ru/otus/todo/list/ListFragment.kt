package ru.otus.todo.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.todo.R
import ru.otus.todo.count.CountFragment
import ru.otus.todo.databinding.FragmentListBinding
import ru.otus.todo.main.Task
import java.util.stream.Collectors

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by activityViewModels()
    private val taskAdapter: TaskAdapter by lazy { TaskAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(context)

        val itemTouchHelper = ItemTouchHelper(object : SwipeCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val removedTask = taskAdapter.getTask(position)
                removedTask.id?.let { viewModel.closeTask(it) }
                taskAdapter.removeItem(position)
                taskAdapter.notifyItemRemoved(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.apply {
            this.layoutManager = layoutManager
            adapter = taskAdapter
        }
        viewModel.loadData()

        viewModel.getTasksLive().observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitData(tasks as MutableList<Task>)
            taskAdapter.notifyDataSetChanged()
        }

        binding.home.setOnClickListener() {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, CountFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}