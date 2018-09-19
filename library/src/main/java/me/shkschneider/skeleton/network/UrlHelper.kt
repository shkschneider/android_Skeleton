package me.shkschneider.skeleton.network

import android.net.Uri
import android.webkit.URLUtil
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.helper.Logger
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

object UrlHelper {

    fun builder(): Uri.Builder {
        return Uri.Builder()
    }

    fun builder(url: String): Uri.Builder {
        return Uri.parse(url).buildUpon()
    }

    fun valid(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }

    fun http(url: String): Boolean {
        return URLUtil.isHttpUrl(url)
    }

    fun https(url: String): Boolean {
        return URLUtil.isHttpsUrl(url)
    }

    fun encode(string: String): String? {
         try {
             return URLEncoder.encode(string, CharsetHelper.UTF8)
        } catch (e: UnsupportedEncodingException) {
            Logger.wtf(e)
             return null
        }
    }

    fun decode(string: String): String? {
        try {
            return URLDecoder.decode(string, CharsetHelper.UTF8)
        } catch (e: UnsupportedEncodingException) {
            Logger.wtf(e)
            return null
        }
    }

    fun uri(builder: Uri.Builder): Uri {
        return builder.build()
    }

    fun url(uri: Uri): String {
        return uri.toString()
    }

}
