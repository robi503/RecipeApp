package com.example.recipeapp.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val author: String,
    val description: String,
    val preparationTime: String,
    val date: Date,
)
