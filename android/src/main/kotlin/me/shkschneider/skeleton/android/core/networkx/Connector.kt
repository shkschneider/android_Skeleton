package me.shkschneider.skeleton.networkx

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.shkschneider.skeleton.helperx.SystemServices

class Connector {

    private val connectivity = MutableLiveData<Connectivity>()

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun listenForConnectivity(): LiveData<Connectivity> {
        SystemServices.connectivityManager()?.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
        return connectivity
    }

    fun unlisten() {
        SystemServices.connectivityManager()?.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object: ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            connectivity.postValue(when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Connectivity.Cellular
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Connectivity.Wifi
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> Connectivity.Bluetooth
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> Connectivity.Ethernet
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> Connectivity.Vpn
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> Connectivity.WifiAware
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> Connectivity.LowPan
                else -> Connectivity.Unknown
            })
        }

        override fun onUnavailable() {
            super.onUnavailable()
            connectivity.postValue(null)
        }

    }

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

    }

}
