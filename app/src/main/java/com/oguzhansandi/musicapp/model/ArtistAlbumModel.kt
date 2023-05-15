package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistAlbumModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("cover_medium")
    var cover_medium: String,
    @SerializedName("cover_small")
    var cover_small: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("release_date")
    var release_date: String,

)