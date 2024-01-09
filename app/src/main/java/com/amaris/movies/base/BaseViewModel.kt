package com.amaris.movies.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaris.movies.base.utils.MutableResetLiveData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    @PublishedApi
    internal val networkError = MutableResetLiveData<Result.Error<out Any>?>()
    internal var isErrorHandling = false
        set(value) {
            field = value
            if (!isErrorHandling) networkError.value = null
        }

    protected val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    /**
     * This function helps handle api call
     * @param api (required) the function that return the response of api
     * @return flow of Result<T,E>
     * T is the generic type of success data
     * E is the generic type of error data
     */
    inline fun <T, reified E : Any> callApi(
        crossinline selfErrorHandle: (Result.Error<E>) -> Boolean = { true },
        crossinline api: suspend () -> Response<T>
    ) = flow<Result<T, E>> {
        emit(Result.Loading)
        val response = api.invoke()
        emit(response.asResult())
    }.catch {
        emit(Result.Error(throwable = it))
    }.onEach {
        if (it is Result.Error) {
            // Only emit event to view when the called does not expect to handle error itself
            val selfHandle = selfErrorHandle(it)
            if (!selfHandle) {
                withContext(Dispatchers.Main) {
                    networkError.value = it
                }
            }
        }
    }

    suspend inline fun <T, reified E : Any> apiCall(
        crossinline selfErrorHandle: (Result.Error<E>) -> Boolean = { false },
        crossinline api: suspend () -> Response<T>
    ): Result<T, E> {
        val response = runCatching {
            api().asResult<T, E>()
        }.getOrElse {
            Result.Error(throwable = it)
        }
        // Only emit event to view when the called does not expect to handle error itself
        if (response is Result.Error && !selfErrorHandle(response)) {
            networkError.postValue(response)
        }
        return response
    }

    suspend inline fun <T, reified E : Any> apiCallFlow(
        crossinline selfErrorHandle: (Result.Error<E>) -> Boolean = { false },
        crossinline api: suspend () -> Response<T>
    ): Flow<Result<T, E>> {
        val response = kotlin.runCatching {
            api().asResult<T, E>()
        }.getOrElse {
            Result.Error(throwable = it)
        }
        // Only emit event to view when the called does not expect to handle error itself
        if (response is Result.Error && !selfErrorHandle(response)) {
            networkError.postValue(response)
        }
        return flowOf(response)
    }
}

/**
 * This function helps transform api response to Result<T,E>
 * @param res (required) response of api call
 * @return Result<T,E>
 * T is the generic type of success data
 * E is the generic type of error data
 */
inline fun <T, reified E> Response<T>.asResult(): Result<T, E> = if (!isSuccessful) {
    val response = kotlin.runCatching {
        Gson().fromJson(errorBody()?.toString(), E::class.java)
    }.getOrNull()
    Result.Error(response, HttpException(this))
} else {
    Result.Success(body())
}