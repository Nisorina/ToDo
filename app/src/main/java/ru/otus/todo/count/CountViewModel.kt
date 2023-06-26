package ru.otus.todo.count

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.todo.main.TaskRepository
import javax.inject.Inject

@HiltViewModel
class CountViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {
    private val tasksCountLive = MutableLiveData<Int>()
    private var activeJob: Job? = null
    fun loadData () {
        if (activeJob?.isActive == true) {
            activeJob?.cancel()
        }
        activeJob = viewModelScope.launch {
            try {
                tasksCountLive.value= taskRepository.getTaskCount()
            }
            catch (e: Exception) {
                Log.e("Ошибка", e.localizedMessage)
            }
        }
    }

    fun getTasksCountLive(): LiveData<Int> {
        return tasksCountLive
    }
}