package com.example.recipeapp.auth

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val mainActivityIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textView = findViewById(R.id.registerNow)
        textView.setOnClickListener {
            // Create an intent to start LoginActivity
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()
        buttonLogin = findViewById(R.id.login_button) // Replace with actual ID
        editTextEmail = findViewById(R.id.email) // Replace with actual ID
        editTextPassword = findViewById(R.id.password) // Replace with actual ID
        progressBar = findViewById(R.id.progress_circular)

        buttonLogin.setOnClickListener(){
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            when {
                email.isEmpty() -> showToast("Email is required")
                password.isEmpty() -> showToast("Password is required")
                else -> {
                    // Add your code here to handle the registration process
                    progressBar.isVisible = true
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            progressBar.isVisible = false
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser
                                val mainActivityIntent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(mainActivityIntent)
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                }
            }
        }
    }

private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}