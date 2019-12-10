package me.shkschneider.skeleton.networkx

import android.net.NetworkCapabilities
import me.shkschneider.skeleton.helperx.SystemServices

@Deprecated("Use ConnectivityManager")
sealed class Connectivity {

    object Cellular : Connectivity()
    object Wifi : Connectivity()
    object Bluetooth : Connectivity()
    object Ethernet : Connectivity()
    object Vpn : Connectivity()
    object WifiAware : Connectivity()
    object LowPan : Connectivity()
    object Unknown : Connectivity()
    object None : Connectivity()

    val hasInternet: Boolean
        get() {
            return SystemServices.connectivityManager()?.let { connectivityManager ->
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return@let capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && connectivityManager.activeNetworkInfo.isConnected
            } ?: false
        }

    val cellular: Boolean get() = (type() == Cellular)

    val wifi: Boolean get() = (type() == Wifi)

    val bluetooth: Boolean get() = (type() == Bluetooth)

    val ethernet: Boolean get() = (type() == Ethernet)

    val vpn: Boolean get() = (type() == Vpn)

    val wifiAware: Boolean get() = (type() == WifiAware)

    val lowPan: Boolean get() = (type() == LowPan)

    fun type(): Connectivity {
        SystemServices.connectivityManager()?.let { connectivityManager ->
            // TODO ConnectivityManager.NetworkCallback
            if (connectivityManager.activeNetworkInfo.isConnected) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Cellular
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Wifi
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> Bluetooth
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> Ethernet
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> Vpn
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> WifiAware
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> LowPan
                    else -> Unknown
                }
            }
        }
        return None
    }

}
