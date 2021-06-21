package com.example.TP1.api

import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @POST("authenticate")
    suspend fun checkUser(@Query("user") pseudo: String, @Query("password") mdp: String ): Response

    @GET("lists")
    suspend fun getListes(@Query("hash") hash:String): Response

    @GET("lists/{id}/items")
    suspend fun getItems(@Path("id") id:String, @Query("hash") hash:String ) : Response
}