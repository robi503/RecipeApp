package com.example.recipeapp.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.RetrofitClient
import com.example.recipeapp.todo.data.PostRecipe
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var preparationTimeEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        // Initialize views
        exitButton = findViewById(R.id.exitAddButton)
        nameEditText = findViewById(R.id.recipeNameEditText)
        descriptionEditText = findViewById(R.id.recipeDescriptionEditText)
        preparationTimeEditText = findViewById(R.id.recipePreparationTimeEditText)
        submitButton = findViewById(R.id.submitRecipeButton)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val preparationTime = preparationTimeEditText.text.toString()
            val authorEmail = FirebaseAuth.getInstance().currentUser?.email ?: "Unknown"
            val recipeData = PostRecipe(name, authorEmail, description, preparationTime)


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    RetrofitClient.recipeApiService.postRecipe(recipeData)
                    withContext(Dispatchers.Main) {
                        showToast("Recipe successfully added")
                    }// Handle success on the main thread if necessary
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        showToast("Error: ${e.message}")
                    }// Handle error
                }
            }
        }
        exitButton.setOnClickListener {
            val mainActivityIntent = Intent(this@AddRecipeActivity, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
        }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
