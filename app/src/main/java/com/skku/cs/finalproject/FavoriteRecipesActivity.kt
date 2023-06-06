package com.skku.cs.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.skku.cs.finalproject.adapter.RecipesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteRecipesActivity : AppCompatActivity() {
    private val recipesDatabase by lazy { (application as RecipesListApplication).dao }
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_recipes)
        setupBackButton()
        setupRecyclerView()
    }

    private fun setupBackButton() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFavoriteRecipes)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        recipesAdapter = RecipesAdapter(
            onFavoriteClick = { recipe ->
                lifecycleScope.launch(Dispatchers.IO) {
                    val isFavorite = recipe.isFavorite
                    recipe.isFavorite = !isFavorite
                    if (!isFavorite) {
                        recipesDatabase.delete(recipe)
                        showToastOnUiThread("Recipe removed from favorites")
                    } else {
                        recipesDatabase.insert(recipe)
                        showToastOnUiThread("Recipe added to favorites")
                    }
                    runOnUiThread {
                        recipesAdapter.notifyDataSetChanged()
                    }
                }
            },
            onItemClick = { recipe ->
                val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                    putExtra("recipe", recipe.sourceId)
                }
                startActivity(intent)
            }
        )
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val recipes = withContext(Dispatchers.IO) {
                recipesDatabase.getAll()
            }
            recipesAdapter.submitList(recipes)
        }
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
}
