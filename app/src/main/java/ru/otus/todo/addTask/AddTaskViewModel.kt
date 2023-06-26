package ru.otus.todo.addTask

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.todo.main.Task
import ru.otus.todo.main.TaskRepository
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel()  {
    private val addedTasksLive = MutableLiveData<Task>()
    fun addTask(content: String) {
        viewModelScope.launch {
            try {
                taskRepository.addTask(content) { task ->
                    addedTasksLive.value = task
                }
            }
            catch (e: Exception) {
                Log.e("Ошибка", e.localizedMessage)
            }
        }
    }

    fun getAddedTasksLive(): LiveData<Task> {
        return addedTasksLive
    }
}