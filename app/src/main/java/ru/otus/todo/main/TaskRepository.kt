package ru.otus.todo.main

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor() {
    private val client = OkHttpClient()
    private val baseUrl = "https://api.todoist.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(ToDoService::class.java)

    suspend fun request(callback: (response: List<Task>?) -> Unit) {
        val response = service.getTasks()
        if (response.isSuccessful) {
            callback(response.body())
        } else {
            Log.e("Ошибка", "${response.code()} ${response.message()}")
        }
    }

    suspend fun addTask(content: String, callback: (response: Task?) -> Unit) {
        val response = service.addTask(Task(content, null))
        if (response.isSuccessful) {
            callback(response.body())
        } else {
            Log.e("Ошибка", "${response.code()} ${response.message()}")
        }
    }

    suspend fun getTaskCount(): Int {
        var result = 0
        request { response ->
            if (response != null) {
                result = response.size
            }
        }
        return result
    }

    suspend fun closeTask(id: String, callback: (response: Response<Void>) -> Unit) {
        val response = service.closeTask("${baseUrl}rest/v2/tasks/$id")
        if (response.isSuccessful) {
            callback(response)
        } else {
            Log.e("Ошибка", "${response.code()} ${response.message()}")
        }
    }

    interface ToDoService {
        //TODO спрятать токен
        @GET("rest/v2/tasks ")
        @Headers(
            "Content-Type:application/json",
            "Accept:application/json",
            "Authorization: Bearer 56f59f44af3f0c5b752520e4723a84a6b170d99f"
        )
        suspend fun getTasks(): Response<List<Task>>

        @POST("rest/v2/tasks ")
        @Headers(
            "Content-Type:application/json",
            "Accept:application/json",
            "Authorization: Bearer 56f59f44af3f0c5b752520e4723a84a6b170d99f"
        )
        suspend fun addTask(@Body data: Task): Response<Task>

        @DELETE
        @Headers(
            "Content-Type:application/json",
            "Accept:application/json",
            "Authorization: Bearer 56f59f44af3f0c5b752520e4723a84a6b170d99f"
        )
        suspend fun closeTask(@Url url: String): Response<Void>
    }
}