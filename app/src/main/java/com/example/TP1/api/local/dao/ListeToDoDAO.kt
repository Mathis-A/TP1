package com.example.TP1.api.local.dao

import androidx.room.*
import com.example.TP1.ItemToDo
import com.example.TP1.ListeToDo

@Dao
interface ListeToDoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveListe(listes: List<ListeToDo>)

    @Query("")
    suspend fun getItems(id:String): List<ItemToDo>
}