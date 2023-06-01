package com.skku.cs.finalproject

import android.app.Application
import com.skku.cs.finalproject.data.RecipesDatabase

class RecipesListApplication: Application() {
    private val database by lazy { RecipesDatabase.getInstance(this) }
    val dao by lazy { database.recipesDao() }
}