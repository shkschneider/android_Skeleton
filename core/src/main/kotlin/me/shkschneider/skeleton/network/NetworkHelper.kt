package me.shkschneider.skeleton.network

import android.os.Build
import android.security.NetworkSecurityPolicy
import android.webkit.WebSettings
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.helperx.SystemProperties
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.NetworkInterface
import java.net.SocketException

object NetworkHelper {

    val userAgent: String?
        get() {
            if (Build.VERSION.SDK_INT >= 17) {
                return WebSettings.getDefaultUserAgent(ContextHelper.applicationContext())
            } else {
                return SystemProperties.get("http.agent")
            }
        }

    // Affects platform's components like DownloadManager or MediaPlayer...
    val isCleartextTrafficPermitted: Boolean
        get() {
            if (Build.VERSION.SDK_INT >= 23) {
                return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted
            } else {
                return true
            }
        }

    // TODO test
    val ipAddresses: List<String>?
        get() {
            try {
                val ipAddresses = mutableListOf<String>()
                NetworkInterface.getNetworkInterfaces().asSequence().forEach { networkInterface ->
                    networkInterface.inetAddresses.asSequence()
                            .filterNot { it.isLoopbackAddress }
                            .forEach { inetAddress ->
                                ipAddresses.add(inetAddress.hostAddress)
                            }
                }
                return ipAddresses
            } catch (e: SocketException) {
                Logger.wtf(e)
                return null
            }
        }

    val ip4Address: String?
        get() = ipAddress<Inet4Address>()

    val ip6Address: String?
        get() = ipAddress<Inet6Address>()

    private inline fun <reified T : Any> ipAddress(): String? {
        try {
            NetworkInterface.getNetworkInterfaces().asSequence().forEach { networkInterface ->
                return networkInterface.inetAddresses.asSequence()
                        .filterNot { it.isLoopbackAddress }
                        .filter { it is T }
                        .firstOrNull()?.hostAddress
            }
            return null
        } catch (e: SocketException) {
            Logger.wtf(e)
            return null
        }
    }

}
