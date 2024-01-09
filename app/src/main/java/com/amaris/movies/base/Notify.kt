package com.amaris.movies.base

import android.content.Context
import com.amaris.movies.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class Notify(open val message: String) {
    class Toast(override val message: String) : Notify(message)
    class PopUp(val title: String, override val message: String) : Notify(message)
    class Alert(
        val title: String,
        override val message: String,
        val error: String? = null,
        val positive: String? = null,
        val negative: String? = null
    ) : Notify(message)
}

fun Result.Error<*>.toNotify(context: Context): Notify {
    val labelError = context.getString(R.string.label_error)
    val labelOk = context.getString(R.string.label_OK)
    return when (throwable) {
        is UnknownHostException -> Notify.Alert(
            labelError,
            context.getString(R.string.label_error_no_internet),
            context.getString(R.string.label_error_no_internet),
            labelOk
        )

        is ConnectException -> Notify.Alert(
            labelError,
            context.getString(R.string.label_error_connection_not_stable),
            context.getString(R.string.label_error_connection_not_stable),
            labelOk
        )

        is SocketTimeoutException -> Notify.Toast(
            context.getString(R.string.label_error_request_timeout)
        )

        is HttpException -> {
            val code = (throwable as? HttpException)?.code()
            when (code) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> Notify.PopUp(
                    labelError,
                    context.getString(R.string.label_error_401)
                )

                HttpURLConnection.HTTP_NOT_FOUND -> Notify.Toast(
                    context.getString(R.string.label_error_404)
                )

                HttpURLConnection.HTTP_FORBIDDEN -> Notify.Alert(
                    labelError,
                    context.getString(R.string.label_error_403),
                    context.getString(R.string.label_error_403),
                    labelOk
                )

                HttpURLConnection.HTTP_INTERNAL_ERROR -> Notify.Toast(context.getString(R.string.label_error_500))
                HttpURLConnection.HTTP_UNAVAILABLE -> Notify.Toast(context.getString(R.string.label_error_503))
                else -> {
                    val otherErrorMessage = context.getString(R.string.label_unknown_error)
                    Notify.Toast(otherErrorMessage)
                }
            }
        }

        else -> Notify.Toast(context.getString(R.string.label_unknown_error))
    }
}

fun Result.Error<*>.isUnauthorized() =
    HttpURLConnection.HTTP_UNAUTHORIZED == (throwable as? HttpException)?.code()