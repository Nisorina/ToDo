package ru.otus.todo.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()

        viewModel.getTasksLive().observe(viewLifecycleOwner) { tasks ->
            binding.listItems.text = tasks.stream().map(Task::content).collect(Collectors.joining(", "))
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