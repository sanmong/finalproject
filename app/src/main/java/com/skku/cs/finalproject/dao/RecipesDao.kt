package com.skku.cs.finalproject.dao

import androidx.room.*
import com.skku.cs.finalproject.data.Recipes

@Dao
interface RecipesDao {
    @Query("SELECT * FROM products")
    fun getAll(): List<Recipes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Recipes)

    @Delete
    fun delete(product: Recipes)
}