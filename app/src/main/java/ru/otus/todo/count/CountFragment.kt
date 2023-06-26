package ru.otus.todo.count

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.todo.R
import ru.otus.todo.addTask.AddTaskFragment
import ru.otus.todo.databinding.FragmentCountBinding
import ru.otus.todo.list.ListFragment
import ru.otus.todo.main.Task
import java.util.stream.Collectors

@AndroidEntryPoint
class CountFragment : Fragment() {
    companion object {
        fun newInstance() = CountFragment()
    }

    private lateinit var binding: FragmentCountBinding
    private val viewModel: CountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()

        viewModel.getTasksCountLive().observe(viewLifecycleOwner) {count ->
            binding.taskCount.text = "$count"
        }

        binding.taskCount.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, ListFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.addTaskButton.setOnClickListener() {
                activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, AddTaskFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}