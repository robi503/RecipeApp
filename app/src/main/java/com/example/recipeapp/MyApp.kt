package com.example.recipeapp

import android.app.Application
import androidx.room.Room
import com.example.recipeapp.todo.local.AppDatabase

class MyApp : Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "recipe-database"
        ).build()
    }
}
