package com.example.TP1.api

import com.example.TP1.ItemToDo
import com.example.TP1.ListeToDo

data class Response(
    val hash : String,
    val lists : Array<ListeToDo>,
    val items : Array<ItemToDo>)