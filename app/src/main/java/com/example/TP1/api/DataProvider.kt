package com.example.TP1.api

import android.content.Context
import android.preference.PreferenceManager
import com.example.TP1.ItemToDo
import com.example.TP1.ListeToDo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataProvider() {

    private val sp = PreferenceManager.getDefaultSharedPreferences(context)

    private val BASE_URL = sp.getString("URL API", "http://tomnab.fr/todo-api/").toString()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(APIService::class.java)


    suspend fun checkUserFromApi(user: String, mdp: String): String =
        service.checkUser(user, mdp).hash

    suspend fun getListFromApi(hash: String): List<ListeToDo> =
        service.getListes(hash).lists.toList()

    suspend fun getItems(id: String, hash: String): MutableList<ItemToDo> =
        service.getItems(id, hash).items.toMutableList()
}