package com.example.recipeapp.todo.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecipeApiService {
    @GET("item")
    suspend fun getAllRecipes(): List<Recipe>

    @POST("item")
    suspend fun postRecipe(@Body postRecipe: PostRecipe)

}