package com.skku.cs.finalproject

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.skku.cs.finalproject.api.RecipesApiService
import com.skku.cs.finalproject.response.RecipeInformationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeDetailActivity : AppCompatActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/") // Base URL should be updated
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: RecipesApiService = retrofit.create(RecipesApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeId = intent.getIntExtra("recipeId", -1)

        if (recipeId != -1) {
            val call = service.getRecipeInformation(recipeId)
            call.enqueue(object : Callback<RecipeInformationResponse> {
                override fun onResponse(
                    call: Call<RecipeInformationResponse>,
                    response: Response<RecipeInformationResponse>
                ) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val recipeInfo = response.body()
                    if (recipeInfo != null) {
                        val imageView = findViewById<ImageView>(R.id.recipeImageView)
                        val titleTextView = findViewById<TextView>(R.id.recipeDetailName)
                        val instructionsTextView = findViewById<TextView>(R.id.recipeDetailInstructions)
                        val ingredientsTextView = findViewById<TextView>(R.id.recipeDetailIngredients)

                        Glide.with(this@RecipeDetailActivity)
                            .load(recipeInfo.imageUrl)
                            .into(imageView)

                        titleTextView.text = recipeInfo.title
                        instructionsTextView.text = recipeInfo.instructions
                        ingredientsTextView.text = recipeInfo.ingredients.joinToString { it.originalString }
                    }
                }

                override fun onFailure(call: Call<RecipeInformationResponse>, t: Throwable) {
                    throw t
                }
            })
        }
    }
}
