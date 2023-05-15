package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistSongsModel (
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("preview")
    var preview: String,
    @SerializedName("duration")
    var duration: Int,
    @SerializedName("md5_image")
    var md5_image : String
)