package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.auth.LoginActivity
import kotlinx.coroutines.*
import com.example.recipeapp.todo.data.Recipe
import com.example.recipeapp.ui.theme.RecipeAdapter
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var adapter: RecipeAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: Button
    private lateinit var userEmailTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        logoutButton = findViewById(R.id.logout_button)
        userEmailTextView = findViewById(R.id.userEmail)
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // User is not logged in, redirect to LoginActivity
            val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(loginIntent)
            finish() // Close MainActivity
            return // Ensure no further code in onCreate is executed
        }

        userEmailTextView.text = currentUser.email ?: "No email"
        setupRecyclerView()
        fetchData()
        logoutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(loginIntent)
            finish() // Close MainActivity
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager
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
