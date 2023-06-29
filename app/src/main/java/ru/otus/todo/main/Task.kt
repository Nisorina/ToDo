package ru.otus.todo.main

import com.squareup.moshi.Json

data class Task(
    @field:Json(name="content")
    val content:String,
    @field:Json(name="id")
    val id:String?
)
