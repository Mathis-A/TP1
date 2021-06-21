package com.example.TP1

data class ListeToDo(
    val id: String,
    var nom: String,
    val items: MutableList<ItemToDo>
)