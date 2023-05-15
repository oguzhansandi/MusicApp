package com.oguzhansandi.musicapp.service

import com.oguzhansandi.musicapp.model.ArtistSongsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SongsAPI {
    @GET("https://api.deezer.com/album/{album_id}/tracks")
    suspend fun getSongs(@Path("album_id") albumId: Int) : Response<ArtistSongsResponseModel>
/*
    @GET("https://api.deezer.com/album/{songs_id}/tracks")
    suspend fun getSongs(@Path("songs_id") songsId: Int) : Response<>
*/
}
