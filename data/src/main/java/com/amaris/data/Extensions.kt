package com.amaris.data

import com.amaris.data.local.entities.MovieEntity
import com.amaris.data.remote.responses.MovieDto

fun MovieEntity.toDto() = MovieDto(
    title = title,
    year = year,
    imdbId = remoteId,
    type = type,
    poster = poster
)