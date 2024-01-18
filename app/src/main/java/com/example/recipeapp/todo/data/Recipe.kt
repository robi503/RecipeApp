package com.example.recipeapp.todo.data

data class Recipe(
    val id: String,
    val name: String,
    val author: String,
    val description: String,
    val preparationTime: String,
    val date: String,
    val version: Int
)
