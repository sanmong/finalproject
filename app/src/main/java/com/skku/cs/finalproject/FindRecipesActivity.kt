package com.skku.cs.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.skku.cs.finalproject.adapter.RecipesAdapter
import com.skku.cs.finalproject.data.Recipes
import com.skku.cs.finalproject.fetcher.RecipesFetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindRecipesActivity : AppCompatActivity() {
    private val recipesFetcher =
        RecipesFetcher("https://api.spoonacular.com/", "8bddad5450db4985afefe1ccaadfbf9f")
    private val recipesDatabase by lazy { (application as RecipesListApplication).dao }
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_recipes)
        setupBackButton()
        Log.d("FindRecipesActivity", "Setting up RecyclerView") // Log statement
        setupRecyclerView()
        Log.d("FindRecipesActivity", "Setting up Search") // Log statement
        setupSearch()
    }

    private fun setupSearch() {
        val editText = findViewById<EditText>(R.id.recipeNameEditText)
        val button = findViewById<Button>(R.id.recipeSearchButton)
        button.setOnClickListener {
            val query = editText.text.toString()
            recipesFetcher.searchRecipes(query, callback = { products ->
                recipesAdapter.submitList(products)
            })
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recipeRecyclerView)
        recipesAdapter = RecipesAdapter(
            onFavoriteClick = { recipe ->
                lifecycleScope.launch(Dispatchers.IO) {
                    val isFavorite = recipe.isFavorite
                    recipe.isFavorite = !isFavorite
                    if (!isFavorite) {recipesDatabase.insert(recipe)
                        showToastOnUiThread("Recipe added to favorites")
                    } else {recipesDatabase.delete(recipe)
                        showToastOnUiThread("Recipe removed from favorites")
                    }
                    runOnUiThread {
                        updateFavoriteStatus(recipe)
                        recipesAdapter.notifyDataSetChanged()
                    }
                }
            },
            onItemClick = { recipe ->
                val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                    putExtra("recipeId", recipe.id)
                }
                startActivity(intent)
            }
        )
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun setupBackButton() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFindRecipes)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showToastOnUiThread(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateFavoriteStatus(recipe: Recipes) {
        recipesAdapter.currentList.firstOrNull { it.id == recipe.id }?.isFavorite = recipe.isFavorite
    }

}
