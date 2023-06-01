package com.skku.cs.finalproject.response

import com.google.gson.annotations.SerializedName

class SearchRecipesResponse {
    @SerializedName("recipes")
    lateinit var recipes: List<RecipesResponse>

    @SerializedName("totalRecipes")
    var totalRecipes: Int = 0

    @SerializedName("type")
    lateinit var type: String

    @SerializedName("offset")
    var offset: Int = 0

    @SerializedName("number")
    var number: Int = 0
}