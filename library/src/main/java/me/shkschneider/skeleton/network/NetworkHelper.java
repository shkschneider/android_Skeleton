package me.shkschneider.skeleton.network;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import android.webkit.WebSettings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemServices;

public class NetworkHelper {

    protected NetworkHelper() {
        // Empty
    }

    // <http://stackoverflow.com/a/21899684>
    @Deprecated // Avoid
    @Nullable
    public static String hostname() {
        try {
            final Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        }
        catch (final NoSuchMethodException e) {
            LogHelper.wtf(e);
            return null;
        }
        catch (final InvocationTargetException e) {
            LogHelper.wtf(e);
            return null;
        }
        catch (final IllegalAccessException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String userAgent() {
        if (AndroidHelper.api() >= AndroidHelper.API_17) {
            return userAgent17();
        }
        return System.getProperty("http.agent");
    }

    // <https://stackoverflow.com/a/43238397>
    @TargetApi(AndroidHelper.API_17)
    private static String userAgent17() {
        return WebSettings.getDefaultUserAgent(ContextHelper.applicationContext());
    }

    public static boolean connectedOrConnecting() {
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

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static boolean wifi() {
        final WifiManager wifiManager = SystemServices.wifiManager();
        if (wifiManager == null) {
            LogHelper.warning("wifiManager was NULL");
            return false;
        }
        return (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
    }

    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    @Deprecated // Avoid
    @Nullable
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
        @SuppressLint("HardwareIds")
        final String macAddress = wifiInfo.getMacAddress();
        if (TextUtils.isEmpty(macAddress)) {
            LogHelper.warning("MacAddress was NULL");
            return null;
        }
        return macAddress;
    }

    @Nullable
    public static List<String> ipAddresses() {
        try {
            final List<String> ipAddresses = new ArrayList<>();
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements();) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements();) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (! inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        ipAddresses.add(ipAddress);
                    }
                }
            }
            return ipAddresses;
        }
        catch (final SocketException e) {
            LogHelper.wtf(e);
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
                    if (! inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return ipAddress;
                    }
                }
            }
            return null;
        }
        catch (final SocketException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
