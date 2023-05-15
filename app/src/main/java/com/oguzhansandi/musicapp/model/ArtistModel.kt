package com.oguzhansandi.musicapp.model

import com.google.gson.annotations.SerializedName

data class ArtistModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("link")
    val link : String,
    @SerializedName("share")
    val share : String,
    @SerializedName("picture_medium")
    val picture_medium : String,
    @SerializedName("nb_album")
    val nb_album : Int,
    @SerializedName("nb_fan")
    val nb_fan : Int,
    @SerializedName("radio")
    val radio : Boolean,
    @SerializedName("tracklist")
    val tracklist : String,
    @SerializedName("type")
    val type : String
    )