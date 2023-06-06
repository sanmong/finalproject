package com.skku.cs.finalproject

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.skku.cs.finalproject.api.RecipesApiService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFindProduct()
        setupFavoriteProduct()
    }

    private fun setupFindProduct() {
        val button = findViewById<Button>(R.id.findRecipesButton)
        button.setOnClickListener {
            val intent = Intent(this, FindRecipesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupFavoriteProduct() {
        val button = findViewById<Button>(R.id.favoriteRecipesButton)
        button.setOnClickListener {
            val intent = Intent(this, FavoriteRecipesActivity::class.java)
            startActivity(intent)
        }
    }
}