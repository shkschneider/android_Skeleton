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

        val hasInternet: Boolean
            get() {
                return SystemServices.connectivityManager()?.let { connectivityManager ->
                    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    return@let capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            && connectivityManager.activeNetworkInfo.isConnected
                } ?: false
            }

        val cellular: Boolean get() = (type() == CELLULAR)

        val wifi: Boolean get() = (type() == WIFI)

        val bluetooth: Boolean get() = (type() == BLUETOOTH)

        val ethernet: Boolean get() = (type() == ETHERNET)

        val vpn: Boolean get() = (type() == VPN)

        val wifiAware: Boolean get() = (type() == WIFI_AWARE)

        val lowPan: Boolean get() = (type() == LOWPAN)

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