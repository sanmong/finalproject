package com.skku.cs.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.skku.cs.finalproject.R
import com.skku.cs.finalproject.data.Recipes

class RecipesAdapter(
    private val onFavoriteClick: (Recipes) -> Unit,
    private val onItemClick: (Recipes) -> Unit
) : ListAdapter<Recipes, RecipesAdapter.ViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view, onFavoriteClick, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        if (current.isFavorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_filled_star)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_empty_star)
        }
    }

    class ViewHolder(
        view: View,
        private val onFavoriteClick: (Recipes) -> Unit,
        private val onItemClick: (Recipes) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private lateinit var recipes: Recipes
        private val imageView: ImageView = view.findViewById(R.id.recipeImageView)
        private val nameTextView: TextView = view.findViewById(R.id.recipeNameTextView)
        private val idTextView: TextView = view.findViewById(R.id.recipeIdTextView)
        val favoriteButton: ImageButton = view.findViewById(R.id.recipeFavoriteButton)

        init {
            view.setOnClickListener {
                onItemClick(this.recipes)
            }
            favoriteButton.setOnClickListener {
                onFavoriteClick(this.recipes)
            }
        }

        fun bind(Recipes: Recipes) {
            this.recipes = Recipes
            nameTextView.text = Recipes.title
            idTextView.text = Recipes.sourceId.toString()

            Glide.with(imageView.context)
                .load(Recipes.imageUrl)
                .into(imageView)
            if (recipes.isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_filled_star)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_empty_star)
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
                    && oldItem.isFavorite == newItem.isFavorite
        }
    }
}
