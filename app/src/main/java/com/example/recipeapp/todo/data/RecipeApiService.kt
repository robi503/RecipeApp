package com.example.recipeapp.todo.data

import retrofit2.http.GET

interface RecipeApiService {
    @GET("item")
    suspend fun getAllRecipes(): List<Recipe>
}