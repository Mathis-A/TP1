package com.example.TP1.api.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.TP1.ItemToDo
import com.example.TP1.ListeToDo
import com.example.TP1.ProfilListeToDo
import com.example.TP1.api.local.dao.ProfilListeToDoDAO
import com.example.TP1.api.local.dao.ListeToDoDAO
import com.example.TP1.api.local.dao.ItemToDoDAO


@Database(
    entities = [
        ProfilListeToDo::class,
        ListeToDo::class,
        ItemToDo::class
    ],
    version = 1
)

abstract class ListeDatabase: RoomDatabase() {
    abstract fun profilListeToDoDao(): ProfilListeToDoDAO
    abstract fun listeToDoDao(): ListeToDoDAO
    abstract fun itemToDoDao(): ItemToDoDAO
}