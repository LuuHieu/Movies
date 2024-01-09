package com.amaris.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amaris.data.remote.responses.MovieDto
import kotlinx.coroutines.delay

// The initial key used for loading.
// This is the article id of the first article that will be loaded
private const val STARTING_KEY = 0
private const val LOAD_DELAY_MILLIS = 3_000L

/**
 * A [MoviePagingSource] that loads movies for paging. The [Int] is the paging key or query that is used to fetch more
 * data, and the [com.amaris.data.remote.responses.MovieDto] specifies that the [MoviePagingSource] fetches an [Article] [List].
 */
class MoviePagingSource {
}