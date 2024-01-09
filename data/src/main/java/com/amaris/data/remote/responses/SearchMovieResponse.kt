package com.amaris.data.remote.responses

import com.amaris.data.model.Movie
import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    @SerializedName("Search")
    val search: List<Movie>,
    val totalResults: String,
    @SerializedName("Response")
    val response: String,
)