package com.example.TP1.api

import android.content.Context
import android.preference.PreferenceManager
import com.example.TP1.ListeToDo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataProvider(context: Context) {

    val sp = PreferenceManager.getDefaultSharedPreferences(context)

    private val BASE_URL = sp.getString("URL API", "http://tomnab.fr/todo-api/").toString()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(APIService::class.java)


    fun getListFromApi(): List<ListeToDo> {
        return service.getListes().listes
    }

    fun checkUserFromApi(user:String, mdp:String): String {
        return service.checkUser(user,mdp).toString()
    }
}