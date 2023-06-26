package ru.otus.todo.addTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ru.otus.todo.R
import ru.otus.todo.count.CountFragment
import ru.otus.todo.databinding.FragmentAddTaskBinding
import java.util.stream.Collectors


class AddTaskFragment : Fragment() {
    companion object {
        fun newInstance() = AddTaskFragment()
    }
    private lateinit var binding: FragmentAddTaskBinding
    private val viewModel: AddTaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAddedTasksLive().observe(viewLifecycleOwner) {
                addedTask ->
            Toast.makeText(activity, "добавлена задача ${addedTask.content}", Toast.LENGTH_SHORT).show()
        }

        binding.addTaskButton.setOnClickListener() {
            if(binding.taskContent.text != null) {
                viewModel.addTask(binding.taskContent.text.toString())
                binding.taskContent.text = null
            }
        }

        binding.home.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, CountFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }



    }
}