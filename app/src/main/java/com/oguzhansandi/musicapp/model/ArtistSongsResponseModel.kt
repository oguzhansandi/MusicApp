package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistSongsResponseModel (
    @SerializedName("data")
    var data : ArrayList<ArtistSongsModel>
)