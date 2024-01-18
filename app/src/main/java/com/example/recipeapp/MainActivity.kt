package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import com.example.recipeapp.todo.data.Recipe

class MainActivity : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()
    }

    private fun fetchData() {
        coroutineScope.launch {
            try {
                val recipes = RetrofitClient.recipeApiService.getAllRecipes()
                withContext(Dispatchers.Main) {
                    // Update UI with the fetched recipes
                    // For example, update a RecyclerView adapter here
                    displayRecipes(recipes)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Handle the error, show a message to the user
                    showError(e.message ?: "An error occurred")
                }
            }
        }
    }

    private fun displayRecipes(recipes: List<Recipe>) {
        // Implement the logic to display recipes in your UI
        // For example, set the data to a RecyclerView adapter
    }

    private fun showError(message: String) {
        // Implement the logic to show error messages to the user
        // For example, use a Toast or a Snackbar
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel() // Cancel the coroutine scope when the activity is destroyed
    }
}
