package com.amaris.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amaris.data.MovieRemoteMediator
import com.amaris.data.local.db.MovieRoomDatabase
import com.amaris.data.model.Movie
import com.amaris.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val database: MovieRoomDatabase
) {
    /**
     * Search movies whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    @OptIn(ExperimentalPagingApi::class)
    fun getSearchResultStream(apiKey: String, query: String, type: String): Flow<PagingData<Movie>> {
        Log.d("MovieRepository", "New query: $query")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.movieDao().moviesByName(dbQuery) }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                apiKey = apiKey,
                query = query,
                type = type,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}