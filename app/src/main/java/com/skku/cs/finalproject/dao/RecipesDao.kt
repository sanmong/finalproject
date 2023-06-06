package com.skku.cs.finalproject.dao

import androidx.room.*
import com.skku.cs.finalproject.data.Recipes

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): List<Recipes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: Recipes)

    @Delete
    fun delete(recipes: Recipes)
}