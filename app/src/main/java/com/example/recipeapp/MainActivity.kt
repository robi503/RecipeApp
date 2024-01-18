package com.example.recipeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import com.example.recipeapp.todo.data.Recipe
import com.example.recipeapp.ui.theme.RecipeAdapter


class MainActivity : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = RecipeAdapter()
        recyclerView.adapter = adapter
    }

    private fun fetchData() {
        coroutineScope.launch {
            try {
                val recipes = RetrofitClient.recipeApiService.getAllRecipes()
                withContext(Dispatchers.Main) {
                    displayRecipes(recipes)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError(e.message ?: "An error occurred")
                }
            }
        }
    }

    private fun displayRecipes(recipes: List<Recipe>) {
        adapter.submitList(recipes)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel() // Cancel the coroutine scope when the activity is destroyed
    }
}
