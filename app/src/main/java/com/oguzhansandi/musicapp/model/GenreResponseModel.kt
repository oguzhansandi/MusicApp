package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class GenreResponseModel(
    @SerializedName("data")
    val data : ArrayList<GenreModel>
)