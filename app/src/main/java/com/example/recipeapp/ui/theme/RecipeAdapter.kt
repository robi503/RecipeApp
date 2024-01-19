package com.example.recipeapp.ui.theme

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.todo.data.Recipe

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.textViewName.text = recipe.name
        holder.textViewAuthor.text = recipe.author
        holder.textViewDescription.text = recipe.description
        holder.textViewPreparationTime.text = recipe.preparationTime
    }

    override fun getItemCount(): Int = recipes.size

    fun submitList(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewAuthor: TextView = view.findViewById(R.id.textViewAuthor)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val textViewPreparationTime: TextView = view.findViewById(R.id.textViewPreparationTime)
    }
}

