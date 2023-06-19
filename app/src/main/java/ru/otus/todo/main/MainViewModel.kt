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
    private var activeJob: Job? = null

    fun loadData () {
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

    fun cancel() {
        activeJob?.cancel()
        activeJob = null
    }

    fun getTasksLive(): LiveData<List<Task>> {
        return tasksLive
    }

}