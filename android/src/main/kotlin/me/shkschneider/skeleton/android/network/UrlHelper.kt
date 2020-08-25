package me.shkschneider.skeleton.android.network

import android.net.Uri
import android.webkit.URLUtil
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import me.shkschneider.skeleton.kotlin.text.Charsets
import java.net.URLDecoder
import java.net.URLEncoder

object UrlHelper {

    fun builder(): Uri.Builder =
        Uri.Builder()

    fun builder(url: String): Uri.Builder =
        Uri.parse(url).buildUpon()

    fun valid(url: String): Boolean =
        URLUtil.isValidUrl(url)

    fun http(url: String): Boolean =
        URLUtil.isHttpUrl(url)

    fun https(url: String): Boolean =
        URLUtil.isHttpsUrl(url)

    fun encode(string: String): String? =
        tryOrNull {
            URLEncoder.encode(string, Charsets.UTF8)
        }

    fun decode(string: String): String? =
        tryOrNull {
            URLDecoder.decode(string, Charsets.UTF8)
        }

    fun uri(builder: Uri.Builder): Uri =
        builder.build()

    fun url(uri: Uri): String =
        uri.toString()

}
