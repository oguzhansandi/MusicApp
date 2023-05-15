package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistAlbumResponseModel(
    @SerializedName("data")
    var data : ArrayList<ArtistAlbumModel>
)