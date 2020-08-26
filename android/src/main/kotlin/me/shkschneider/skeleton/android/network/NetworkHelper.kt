package me.shkschneider.skeleton.android.network

import android.security.NetworkSecurityPolicy
import android.webkit.WebSettings
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import me.shkschneider.skeleton.kotlin.log.Logger
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.NetworkInterface
import java.net.SocketException

object NetworkHelper {

    val userAgent: String?
        get() = WebSettings.getDefaultUserAgent(ContextProvider.applicationContext())

    // Affects platform's components like DownloadManager or MediaPlayer...
    val isCleartextTrafficPermitted: Boolean
        get() = NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted

    @Deprecated("Unreliable.")
    val ipAddresses: List<String>?
        get() = tryOrNull {
            val ipAddresses = mutableListOf<String>()
            NetworkInterface.getNetworkInterfaces().asSequence().forEach { networkInterface ->
                networkInterface.inetAddresses.asSequence()
                    .filterNot { it.isLoopbackAddress }
                    .forEach { inetAddress ->
                        ipAddresses.add(inetAddress.hostAddress)
                    }
            }
            ipAddresses
        }

    @Deprecated("Unreliable.")
    val ip4Address: String?
        get() = ipAddress<Inet4Address>()

    @Deprecated("Unreliable.")
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