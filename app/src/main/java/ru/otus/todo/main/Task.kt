package ru.otus.todo.main

import com.squareup.moshi.Json

data class Task(
    @field:Json(name="content")
    val content:String
)
