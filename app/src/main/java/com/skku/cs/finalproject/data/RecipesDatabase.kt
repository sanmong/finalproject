package com.skku.cs.finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skku.cs.finalproject.dao.RecipesDao

@Database(entities = [Recipes::class], version = 2, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        @Volatile
        private var instance: RecipesDatabase? = null

        fun getInstance(context: Context): RecipesDatabase {
            return instance ?: synchronized(this) {
                val migration12 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE recipes ADD COLUMN is_favorite INTEGER NOT NULL DEFAULT 0")
                    }
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipesDatabase::class.java,
                    "recipes_db"
                ).addMigrations(migration12).build()
                this.instance = instance
                return instance
            }
        }
    }
}