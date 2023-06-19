package ru.otus.todo.main

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() {
    private val client = OkHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.todoist.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(ToDoService::class.java)

    suspend fun request(callback: (response:List<Task>?) -> Unit) {
        val  response = service.getTasks()
        if(response.isSuccessful) {
            callback(response.body())
        }
        else {
            Log.e("Ошибочка", "${response.code()} ${response.message()}")
        }
    }

    interface ToDoService {
        @GET("rest/v2/tasks ")
        @Headers("Content-Type:application/json",
            "Accept:application/json",
            "Authorization: Bearer 1612d4f2eeaa9d46a7fa7f3273618d17d80f5a9f")
        suspend fun getTasks(): Response<List<Task>>
    }
}