package ru.otus.todo.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.todo.main.Task
import ru.otus.todo.main.TaskRepository
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {
    private val tasksLive = MutableLiveData<List<Task>>()
    private var activeJob: Job? = null
    fun loadData () {
        if (activeJob?.isActive == true) {
            activeJob?.cancel()
        }
        activeJob = viewModelScope.launch {
            try {
                taskRepository.request() { response ->
                    tasksLive.value = response
                }
            }
            catch (e: Exception) {
                Log.e("Ошибка", e.localizedMessage)
            }
        }
    }

    fun closeTask (id:String) {
      viewModelScope.launch {
            try {
                taskRepository.closeTask(id) { response ->
                   println("выполнился ${ response.code()}")
                }
            }
            catch (e: Exception) {
                Log.e("Ошибка", e.localizedMessage)
            }
        }
    }

    fun getTasksLive(): LiveData<List<Task>> {
        return tasksLive
    }
}