package com.example.TP1.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface APIService {
    @GET("users?pseudo={pseudo}&pass={mdp}")
    fun checkUser(@Path("pseudo") pseudo: String, @Path("mdp") mdp: String ): String


    @GET("users/2/lists")
    fun getListes(): ListeToDoResponse
}