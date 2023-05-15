package com.oguzhansandi.musicapp.service

import com.oguzhansandi.musicapp.model.ArtistAlbumModel
import com.oguzhansandi.musicapp.model.ArtistAlbumResponseModel
import com.oguzhansandi.musicapp.model.ArtistModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumAPI {
    @GET("artist/{artist_id}")
    suspend fun getAlbumData(@Path("artist_id") artistId: Int) : Response<ArtistModel>

    @GET("artist/{artist_id}/albums")
    suspend fun getAlbums(@Path("artist_id") artistId: Int) : Response<ArtistAlbumResponseModel>
}
