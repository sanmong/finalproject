package com.skku.cs.finalproject.api

import com.skku.cs.finalproject.response.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApiService {
    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
    @Query("query") query: String,
    @Query("number") count: Int
    ): Call<SearchRecipesResponse>
}