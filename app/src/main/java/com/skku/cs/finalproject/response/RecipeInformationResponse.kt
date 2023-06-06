package com.skku.cs.finalproject.response

import com.google.gson.annotations.SerializedName

data class RecipeInformationResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("instructions") val instructions: String,
    @SerializedName("extendedIngredients") val ingredients: List<Ingredient>
)

data class Ingredient (
    @SerializedName("originalString") val originalString: String
)
