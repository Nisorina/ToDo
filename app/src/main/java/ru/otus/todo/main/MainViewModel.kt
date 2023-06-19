package ru.otus.todo.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val tasksLive = MutableLiveData<List<Task>>()
    private val tasksCountLive = MutableLiveData<Int>()
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
                tasksCountLive.value= taskRepository.getTaskCount()
            }
            catch (e: Exception) {
                Log.e("Ошибка", e.localizedMessage)
            }
        }
    }

     fun getTasksLive(): LiveData<List<Task>> {
        return tasksLive
    }

    fun getTasksCountLive(): LiveData<Int> {
        return tasksCountLive
    }
}