package com.skku.cs.finalproject.api

import com.skku.cs.finalproject.response.RecipeInformationResponse
import com.skku.cs.finalproject.response.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesApiService {
    @GET("/recipes/complexSearch")
    fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int,
        @Query("offset") offset: Int,
        @Query("addRecipeInformation") addRecipeInfo: Boolean,
        @Query("fillIngredients") fillIngredients: Boolean,
        @Query("instructionsRequired") instructionsRequired: Boolean
    ): Call<SearchRecipesResponse>

    @GET("/recipes/{id}/information")
    fun getRecipeInformation(
        @Path("id") recipeId: Int
    ): Call<RecipeInformationResponse>
}
