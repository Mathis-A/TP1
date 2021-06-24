package com.example.TP1.api.local

import android.app.Application
import androidx.room.Room
import com.example.TP1.ItemToDo
import com.example.TP1.ListeToDo
import com.example.TP1.ProfilListeToDo

class DataSource (application: Application) {

    private val roomDatabase =
        Room.databaseBuilder(application, ListeDatabase::class.java, "liste_db").build()

    private val profilListeToDoDao = roomDatabase.profilListeToDoDao()
    private val listeToDoDao = roomDatabase.listeToDoDao()
    private val itemToDoDao = roomDatabase.itemToDoDao()

    suspend fun getLists(id: String): MutableList<ListeToDo> {
        return profilListeToDoDao.getListes(id).toMutableList()
    }

    suspend fun getItems(id: String): MutableList<ItemToDo> {
            return listeToDoDao.getItems(id).toMutableList()
        }

    suspend fun saveProfil(pseudo: String,mdp : String) {
        val newProfil : ProfilListeToDo = ProfilListeToDo(pseudo,mdp)
        return profilListeToDoDao.saveProfil(newProfil)
    }

    suspend fun saveLists(items: MutableList<ListeToDo>,pseudo:String) {
        for (item in items) { item.nom =pseudo }
        return listeToDoDao.saveListe(items)
    }

    suspend fun saveItems(items: MutableList<ItemToDo>, id: String) {
        for (item in items) { item.id=id }
        return itemToDoDao.saveOrUpdate(items)
    }

    suspend fun getUser(pseudo: String, mdp: String) : ProfilListeToDo
    {
        return profilListeToDoDao.getUser(pseudo,mdp)
    }
}