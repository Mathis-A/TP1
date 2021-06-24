package com.example.TP1.api.local.dao

import android.content.ClipData
import androidx.room.*
import com.example.TP1.ItemToDo

@Dao
interface ItemToDoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(items: List<ItemToDo>)

    @Query("")
    suspend fun getOneItem(id:String): ItemToDo


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(item:ItemToDo)

}