package com.example.test.model

import com.google.gson.annotations.SerializedName

data class Exhibit (

    @SerializedName("images")
    val images: String,

    @SerializedName("title")
    val title: String
        )
