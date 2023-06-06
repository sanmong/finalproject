package com.skku.cs.finalproject.response

import com.google.gson.annotations.SerializedName

data class SearchRecipesResponse(
    @SerializedName("results") val recipes: List<RecipesResult>
)

data class RecipesResult(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("missedIngredientCount") val missedIngredientCount: Int
)
