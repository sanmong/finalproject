package com.skku.cs.finalproject

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.skku.cs.finalproject.adapter.RecipesAdapter

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
        recipesAdapter = RecipesAdapter { recipe -> recipesDatabase.delete(recipe) }
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val recipes = recipesDatabase.getAll()
        recipesAdapter.submitList(recipes)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}