package com.skku.cs.finalproject.response

import com.google.gson.annotations.SerializedName
import com.skku.cs.finalproject.data.Recipes

class RecipesResponse {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("image")
    lateinit var image: String

    @SerializedName("imageType")
    lateinit var imageType: String
}