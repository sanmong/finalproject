package com.skku.cs.finalproject

import android.os.Bundle
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
import com.skku.cs.finalproject.fetcher.RecipesFetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FindRecipesActivity : AppCompatActivity() {
    private val recipesFetcher =
        RecipesFetcher("https://api.spoonacular.com/", "6ac7bc84658d4529b01b0d61557f4551")
    private val recipesDatabase by lazy { (application as RecipesListApplication).dao }
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_recipes)
        setupBackButton()
        setupRecyclerView()
        setupSearch()
    }

    private fun setupSearch() {
        val editText = findViewById<EditText>(R.id.recipeNameEditText)
        val button = findViewById<Button>(R.id.recipeSearchButton)
        button.setOnClickListener {
            val query = editText.text.toString()
            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    val recipes = recipesFetcher.searchRecipes(query, callback = { recipes ->
                        recipesAdapter.submitList(recipes)
                    })
                } catch (e: Exception) {
                    Toast.makeText(this@FindRecipesActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recipeRecyclerView)
        recipesAdapter = RecipesAdapter { recipes -> recipesDatabase.insert(recipes) }
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
}