package com.skku.cs.finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skku.cs.finalproject.dao.RecipesDao

@Database(entities = [Recipes::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        @Volatile
        private var instance: RecipesDatabase? = null

        fun getInstance(context: Context): RecipesDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipesDatabase::class.java,
                    "recipes_db"
                ).allowMainThreadQueries().build()
                this.instance = instance
                return instance
            }
        }
    }
}