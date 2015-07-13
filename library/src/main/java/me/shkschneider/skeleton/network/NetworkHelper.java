package me.shkschneider.skeleton.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemServices;
import me.shkschneider.skeleton.java.StringHelper;

public class NetworkHelper {

    protected NetworkHelper() {
        // Empty
    }

    @Deprecated
    @Nullable
    public static String hostname() {
        try {
            final InetAddress inetAddress = InetAddress.getLocalHost();
            if (inetAddress == null) {
                LogHelper.w("InetAddress was NULL");
                return null;
            }
            return inetAddress.getHostName();
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    @Deprecated
    public static String userAgent() {
        return new WebView(ApplicationHelper.context()).getSettings().getUserAgentString();
    }

    public static boolean connectedOrConnecting() {
        final ConnectivityManager connectivityManager = SystemServices.connectivityManager();
        if (connectivityManager == null) {
            LogHelper.w("ConnectivityManager was NULL");
            return false;
        }
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.w("NetworkInfo was NULL");
            return false;
        }

        return networkInfo.isConnectedOrConnecting();
    }

    public static boolean wifi() {
        final WifiManager wifiManager = SystemServices.wifiManager();
        if (wifiManager == null) {
            LogHelper.w("wifiManager was NULL");
            return false;
        }

        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    @Deprecated
    @Nullable
    public static String macAddress() {
        final WifiManager wifiManager = SystemServices.wifiManager();
        if (wifiManager == null) {
            LogHelper.w("wifiManager was NULL");
            return null;
        }

        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            LogHelper.w("WifiInfo was NULL");
            return null;
        }

        final String macAddress = wifiInfo.getMacAddress();
        if (StringHelper.nullOrEmpty(macAddress)) {
            LogHelper.w("MacAddress was NULL");
            return null;
        }

        return macAddress;
    }

    @Nullable
    public static List<String> ipAddresses() {
        try {
            final List<String> ipAddresses = new ArrayList<String>();
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                        ipAddresses.add(ipAddress);
                    }
                }
            }
            return ipAddresses;
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    @Nullable
    public static String ipAddress() {
        try {
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (! inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipAddress)) {
                        return ipAddress;
                    }
                }
            }
            return null;
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

}
