package com.example.recipeapp.todo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.todo.data.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
