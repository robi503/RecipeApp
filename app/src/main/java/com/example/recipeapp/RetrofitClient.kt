package com.example.recipeapp

import com.example.recipeapp.todo.data.RecipeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:3000/") // Replace with your server's IP
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val recipeApiService: RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }
}
