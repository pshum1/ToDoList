package com.example.todolist.models

data class Todo(
    val title: String? = null,
    val description: String? = null,
) {
    companion object{
        const val COLLECTION_NAME = "todo"
        const val RECORD_KEY = "key"
    }
}