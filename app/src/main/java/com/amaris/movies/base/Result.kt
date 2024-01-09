package com.amaris.movies.base


sealed class Result<out T, out E>(
    open val data: T? = null,
    open val errorData: E? = null
) {
    object Loading : Result<Nothing, Nothing>()

    data class Success<T>(override val data: T?) : Result<T, Nothing>(data)

    data class Error<E>(
        override val errorData: E? = null,
        val throwable: Throwable? = null
    ) : Result<Nothing, E>(errorData = errorData)
}