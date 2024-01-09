package com.amaris.data.remote.service

import com.amaris.data.remote.responses.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") apikey: String,
        @Query("s") s: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): SearchMovieResponse
}