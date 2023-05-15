package com.oguzhansandi.musicapp.service

import com.oguzhansandi.musicapp.model.ArtistsResponseModel
import com.oguzhansandi.musicapp.model.GenreModel
import com.oguzhansandi.musicapp.model.GenreResponseModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistsAPI {
    @GET("genre/{genre_id}/artists")
    suspend fun getArtistsData(@Path("genre_id") categoryId: Int) : Response<ArtistsResponseModel>
}