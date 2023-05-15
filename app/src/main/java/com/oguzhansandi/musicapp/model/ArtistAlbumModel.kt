package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistAlbumModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("cover_medium")
    var cover_medium: String,
    @SerializedName("release_date")
    var release_date: String,

)