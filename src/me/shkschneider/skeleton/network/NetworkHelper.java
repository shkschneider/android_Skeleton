package me.shkschneider.skeleton.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.webkit.URLUtil;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.SystemHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class NetworkHelper {

    public static String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (final UnknownHostException e) {
            LogHelper.error("UnknownHostException: " + e.getMessage());
            return null;
        }
    }

    public static String userAgent() {
        final String userAgent = SystemHelper.systemProperty(SystemHelper.SYSTEM_PROPERTY_HTTP_AGENT);
        if (userAgent == null) {
            return String.format("%s-%debug", AndroidHelper.PLATFORM, AndroidHelper.api());
        }

        return userAgent;
    }

    public static Boolean online() {
        final NetworkInfo networkInfo = ((ConnectivityManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_CONNECTIVITY)).getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return null;
        }

        return networkInfo.isConnected();
    }

    public static Boolean wifi() {
        final WifiManager wifiManager = ((WifiManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WIFI));
        if (wifiManager == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return null;
        }

        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    public static String macAddress() {
        final WifiManager wifiManager = (WifiManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WIFI);
        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String macAddress = wifiInfo.getMacAddress();
        if (StringHelper.nullOrEmpty(macAddress)) {
            LogHelper.warning("MacAddress was NULL");
            return null;
        }

        return macAddress;
    }

    public static boolean validUrl(final String url) {
        if (StringHelper.nullOrEmpty(url)) {
            LogHelper.warning("Url was null");
            return false;
        }

        return URLUtil.isValidUrl(url);
    }

    public static List<String> ipAddresses() {
        final List<String> ipAddresses = new ArrayList<String>();
        try {
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
        }
        catch (final SocketException e) {
            LogHelper.error("SocketException: " + e.getMessage());
            return null;
        }
        return ipAddresses;
    }

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
        }
        catch (final SocketException e) {
            LogHelper.error("SocketException: " + e.getMessage());
        }
        return null;
    }

}
