package com.skku.cs.finalproject.fetcher

import com.skku.cs.finalproject.api.RecipesApiService
import com.skku.cs.finalproject.data.Recipes
import com.skku.cs.finalproject.response.SearchRecipesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesFetcher (endpoint: String, apiKey: String) {
    private class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val url = chain.request().url
                .newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return chain.proceed(request)
        }
    }

    private class UserAgentInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .addHeader(
                    "User-Agent",
                    "AppListRecipe (https://github.com/sanmong/finalproject)"
                )
                .build()

            return chain.proceed(request)
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .addInterceptor(UserAgentInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(endpoint)
        .client(client)
        .build()

    private val service = retrofit.create(RecipesApiService::class.java)

    suspend fun searchRecipes(query: String, count: Int = 10, callback: (List<Recipes>) -> Unit) {
        service.searchRecipes(query, count).enqueue(object : Callback<SearchRecipesResponse> {
            override fun onResponse(
                call: Call<SearchRecipesResponse>,
                res: retrofit2.Response<SearchRecipesResponse>
            ) {
                if (!res.isSuccessful) return
                val body = res.body() ?: return
                val recipes = body.recipes.map { item ->
                    return@map Recipes(
                        sourceId = item.id,
                        title = item.title,
                        imageUrl = item.image,
                    )
                }
                callback(recipes)
            }

            override fun onFailure(call: Call<SearchRecipesResponse>, t: Throwable) {
                throw t
            }
        })
    }
}