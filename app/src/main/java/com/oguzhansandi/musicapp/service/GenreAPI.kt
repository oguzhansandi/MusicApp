package com.oguzhansandi.musicapp.service

import com.oguzhansandi.musicapp.model.GenreModel
import com.oguzhansandi.musicapp.model.GenreResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface GenreAPI {
    @GET("genre")
    suspend fun getData() : Response<GenreResponseModel>
}