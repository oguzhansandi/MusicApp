package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistsResponseModel (
    @SerializedName("data")
    val data : ArrayList<ArtistsModel>
    )

