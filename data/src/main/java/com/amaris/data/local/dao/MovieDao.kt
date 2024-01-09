package com.amaris.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.amaris.data.model.Movie

@Dao
abstract class MovieDao : BaseDao<Movie>() {
    @Query(
        "SELECT * FROM movies WHERE " +
                "title LIKE :queryString " +
                "ORDER BY title ASC"
    )
    abstract suspend fun moviesByName(queryString: String): PagingSource<Int, Movie>

    @Query("DELETE FROM movies")
    abstract suspend fun clearMovies()
}