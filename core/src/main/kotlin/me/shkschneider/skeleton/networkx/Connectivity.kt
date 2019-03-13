package me.shkschneider.skeleton.networkx

import android.net.NetworkCapabilities
import me.shkschneider.skeleton.helperx.SystemServices

enum class Connectivity {
    CELLULAR,
    WIFI,
    BLUETOOTH,
    ETHERNET,
    VPN,
    WIFI_AWARE,
    LOWPAN,
    UNKNOWN,
    NONE;

    companion object {

        fun hasInternet(): Boolean {
            return SystemServices.connectivityManager()?.let { connectivityManager ->
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return@let capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && connectivityManager.activeNetworkInfo.isConnected
            } ?: false
        }

        fun cellular(): Boolean = (type() == CELLULAR)

        fun wifi(): Boolean = (type() == WIFI)

        fun bluetooth(): Boolean = (type() == BLUETOOTH)

        fun ethernet(): Boolean = (type() == ETHERNET)

        fun vpn(): Boolean = (type() == VPN)

        fun wifiAware(): Boolean = (type() == WIFI_AWARE)

        fun lowPan(): Boolean = (type() == LOWPAN)

        fun type(): Connectivity {
            SystemServices.connectivityManager()?.let { connectivityManager ->
                if (connectivityManager.activeNetworkInfo.isConnected) {
                    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    return when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> CELLULAR
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> WIFI
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> WIFI
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ETHERNET
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> VPN
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> VPN
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> LOWPAN
                        else -> UNKNOWN
                    }
                }
            }
            return NONE
        }

    }

}