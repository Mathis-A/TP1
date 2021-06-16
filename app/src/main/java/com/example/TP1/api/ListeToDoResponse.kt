package com.example.TP1.api

import com.example.TP1.ListeToDo
import com.google.gson.annotations.SerializedName

data class ListeToDoResponse(
    @SerializedName("listes")
    val listes :List<ListeToDo>)