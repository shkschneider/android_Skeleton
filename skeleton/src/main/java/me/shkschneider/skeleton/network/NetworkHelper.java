package me.shkschneider.skeleton.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;
import android.webkit.WebView;

import org.apache.http.conn.util.InetAddressUtils;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemServices;
import me.shkschneider.skeleton.java.StringHelper;

public class NetworkHelper {

    @Deprecated
    public static String hostname() {
        try {
            final Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        }
        catch (Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String userAgent() {
        // System.getProperty("http.agent");
        return new WebView(SkeletonApplication.CONTEXT).getSettings().getUserAgentString();
    }

    public static boolean online() {
        final ConnectivityManager connectivityManager = SystemServices.connectivityManager();
        if (connectivityManager == null) {
            LogHelper.warning("ConnectivityManager was NULL");
            return false;
        }
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            LogHelper.warning("NetworkInfo was NULL");
            return false;
        }

        return networkInfo.isConnectedOrConnecting();
    }

    public static boolean wifi() {
        final WifiManager wifiManager = SystemServices.wifiManager();
        if (wifiManager == null) {
            LogHelper.warning("wifiManager was NULL");
            return false;
        }

        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    public static String macAddress() {
        final WifiManager wifiManager = SystemServices.wifiManager();
        if (wifiManager == null) {
            LogHelper.warning("wifiManager was NULL");
            return null;
        }

        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            LogHelper.warning("WifiInfo was NULL");
            return null;
        }

        final String macAddress = wifiInfo.getMacAddress();
        if (StringHelper.nullOrEmpty(macAddress)) {
            LogHelper.warning("MacAddress was NULL");
            return null;
        }

        return macAddress;
    }

    public static boolean validUrl(@NonNull final String url) {
        return URLUtil.isValidUrl(url);
    }

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
            LogHelper.wtf(e);
            return null;
        }
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
            return null;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
