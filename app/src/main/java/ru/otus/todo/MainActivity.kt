package ru.otus.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.todo.databinding.ActivityMainBinding
import ru.otus.todo.main.MainViewModel
import ru.otus.todo.main.Task
import java.util.stream.Collectors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
        viewModel.getTasksLive().observe(this) { tasks ->
            binding.mainText.text = tasks.stream().map(Task::content).collect(Collectors.joining(", "))

            }
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancel()
    }

}