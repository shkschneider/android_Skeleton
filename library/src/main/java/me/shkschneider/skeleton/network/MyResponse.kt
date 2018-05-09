package me.shkschneider.skeleton.network

import com.android.volley.NetworkResponse

class MyResponse(
        networkResponse: NetworkResponse
) : NetworkResponse(
        networkResponse.statusCode,
        networkResponse.data,
        networkResponse.headers,
        networkResponse.notModified,
        networkResponse.networkTimeMs
) {

    fun code(): Int {
        return statusCode
    }

    fun data(): ByteArray {
        return data
    }

    // HttpURLConnection.HTTP_...
    fun status(): Status {
        return when {
            statusCode >= 500 -> Status.SERVER_ERROR
            statusCode >= 400 -> Status.CLIENT_ERROR
            statusCode >= 300 -> Status.REDIRECTION
            statusCode >= 200 -> Status.SUCCESS
            statusCode >= 100 -> Status.INFORMATIONAL
            else -> Status.UNKNOWN
        }
    }

    enum class Status {

        INFORMATIONAL,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNKNOWN

    }

}
