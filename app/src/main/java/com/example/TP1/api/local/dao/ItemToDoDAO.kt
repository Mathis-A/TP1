package com.example.TP1.api.local.dao

import android.content.ClipData
import androidx.room.*
import com.example.TP1.ItemToDo

@Dao
interface ItemToDoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(items: List<ItemToDo>)

    //Récupérer l'item dans la table ITEM désignée par son id
    @Query("SELECT * FROM ITEMTODO WHERE ID = :id")
    suspend fun getOneItem(id:String): ItemToDo

    //Ajouter un item dans une liste
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(item:ItemToDo)

}