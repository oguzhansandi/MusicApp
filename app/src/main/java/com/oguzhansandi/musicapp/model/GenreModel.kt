package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("name")
    val name : String,
    @SerializedName("id")
    val id : Int,
    @SerializedName("picture_medium")
    val picture_medium : String,
    @SerializedName("type")
    val type : String
)