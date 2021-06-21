package com.example.TP1.api.local.dao

import androidx.room.*
import com.example.TP1.ListeToDo
import com.example.TP1.ProfilListeToDo

@Dao
interface ProfilListeToDoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfil(listes: ProfilListeToDo)

    @Query("SELECT * FROM LISTETODO where nom = :pseudo")
    suspend fun getListes(pseudo : String): List<ListeToDo>

    @Query("SELECT * FROM PROFILLISTETODO WHERE nom = :pseudo AND mdp = :mdp")
    suspend fun getUser(pseudo: String, mdp: String): ProfilListeToDo
}