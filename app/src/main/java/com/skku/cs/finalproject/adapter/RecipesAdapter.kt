package com.skku.cs.finalproject.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skku.cs.finalproject.R
import com.skku.cs.finalproject.data.Recipes
import java.net.URL

class RecipesAdapter(private val onClick: (Recipes) -> Unit) :
    ListAdapter<Recipes, RecipesAdapter.ViewHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ViewHolder(view: View, val onClick: (Recipes) -> Unit) : RecyclerView.ViewHolder(view) {
        private lateinit var Recipes: Recipes
        private val imageView: ImageView
        private val nameTextView: TextView
        private val idTextView: TextView
        private val favoriteButton: Button

        init {
            imageView = view.findViewById(R.id.recipeImageView)
            nameTextView = view.findViewById(R.id.recipeNameTextView)
            idTextView = view.findViewById(R.id.recipeIdTextView)
            favoriteButton = view.findViewById(R.id.recipeFavoriteButton)
        }

        fun bind(Recipes: Recipes) {
            this.Recipes = Recipes
            nameTextView.text = Recipes.title
            idTextView.text = Recipes.sourceId.toString()
            favoriteButton.setOnClickListener {
                onClick(this.Recipes)
            }
            try {
                val url = URL(Recipes.imageUrl)
                val drawable = Drawable.createFromStream(url.openStream(), "src")
                imageView.setImageDrawable(drawable)
            } catch (_: Exception) {
                // Ignore the error
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Recipes>() {
        override fun areItemsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.sourceId == newItem.sourceId
                    && oldItem.title == newItem.title
                    && oldItem.imageUrl == newItem.imageUrl
        }
    }
}